package dao;

import dao.Dto.ChitietPhieuNhapDto;
import database.JDBC;
import model.ChiTietPhieu;
import model.PhieuNhap;
import model.PhieuXuat;

import java.sql.*;
import java.util.ArrayList;

public class XuatHangDao {
    Connection conn = JDBC.getJDBCConnection();
    PreparedStatement ps;
    ResultSet rs;

    public String GenerateId() {
        String query = "select maPhieu from phieuxuat where thoiGianTao = (select max(thoiGianTao) from phieuxuat)";
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                String id = rs.getString("maPhieu");
                int Numeric = Integer.parseInt(id.substring(2));
                Numeric++;
                return "PX" + Integer.toString(Numeric);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean AddPhieuXuat(PhieuXuat px) {
        String query = "INSERT INTO phieuxuat VALUES(?,?,?,?)";
        try {
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(query);
            ps.setString(1, px.getMaPhieu());
            ps.setTimestamp(2, px.getThoiGianTao());
            ps.setString(3, px.getNguoiTao());
            ps.setDouble(4, px.getTongTien());
            if (ps.executeUpdate() > 0 && SaveCtPn(px.getCTPhieu())) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true);
//                if (ps != null) ps.close();
//                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public PhieuXuat findById(String id) {
        var query = "select * from phieuxuat where maPhieu = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                PhieuXuat px = new PhieuXuat();
                px.setMaPhieu(rs.getString("maPhieu"));
                px.setThoiGianTao(rs.getTimestamp("thoiGianTao"));
                px.setNguoiTao(rs.getString("nguoiTao"));
                px.setTongTien(rs.getDouble("tongTien"));
                return px;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean SaveCtPn(ArrayList<ChiTietPhieu> list) {
        try {
            var query = "insert into chitietphieuxuat values(?,?,?,?)";
            var pdQuery = "update maytinh set soLuong = soLuong - ? where maMay = ?";
            ps = conn.prepareStatement(query);
            for (var i : list) {
                ps.setString(1, i.getMaPhieu());
                ps.setString(2, i.getMaMay());
                ps.setInt(3, i.getSoLuong());
                ps.setDouble(4, i.getDonGia());
                if (ps.executeUpdate() > 0) {
                    var psp = conn.prepareStatement(pdQuery);
                    psp.setInt(1, i.getSoLuong());
                    psp.setString(2, i.getMaMay());
                    if (psp.executeUpdate() <= 0) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public ArrayList<ChitietPhieuNhapDto> getListCtp(String id){
        var query = " select chitietphieuxuat.*, maytinh.tenMay from chitietphieuxuat\n" +
                " join maytinh on chitietphieuxuat.maMay = maytinh.maMay\n" +
                " where maPhieu=?;";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();
            ArrayList<ChitietPhieuNhapDto> list = new ArrayList<>();
            while (rs.next()){
                ChitietPhieuNhapDto ctpn = new ChitietPhieuNhapDto(rs.getString("maPhieu"), rs.getString("maMay"), rs.getInt("soLuong"), rs.getDouble("donGia"), rs.getString("tenMay"));
                list.add(ctpn);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<PhieuXuat> GetList(Timestamp START, Timestamp END,double min, double max){
        var query = "select * from phieuxuat where phieuxuat.thoiGianTao between ? and ? and phieuxuat.tongTien between ? and ? order by thoiGianTao desc";
        var list = new ArrayList<PhieuXuat>();
        try {
            ps = conn.prepareStatement(query);
            ps.setTimestamp(1, START);
            ps.setTimestamp(2, END);
            ps.setDouble(3, min);
            ps.setDouble(4, max);
            rs = ps.executeQuery();
            while (rs.next()){
                var px = new PhieuXuat();
                px.setMaPhieu(rs.getString("maPhieu"));
                px.setThoiGianTao(rs.getTimestamp("thoiGianTao"));
                px.setNguoiTao(rs.getString("nguoiTao"));
                px.setTongTien(rs.getDouble("tongTien"));
                list.add(px);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean handleQuantityChange(String id, boolean isIncrease,int quantity){
        var query = "update maytinh set soLuong = soLuong + ? where maMay = ?";
        var check = "select soLuong from maytinh where maMay = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(check);
            ps.setString(1, id);
            rs = ps.executeQuery();
            int current = 0;
            if (rs.next()) {
                current = rs.getInt("soLuong");
            }
            int newQuantity = isIncrease ? current + quantity : current - quantity;
            if(newQuantity < 0){
                throw new Exception("Số lượng không đủ");
            }
            ps = conn.prepareStatement(query);
            ps.setInt(1, isIncrease ? quantity : -quantity);
            ps.setString(2, id);
            if (ps.executeUpdate() > 0) {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public boolean handedAdded(ArrayList<ChitietPhieuNhapDto> added){
        var query = "insert into chitietphieuxuat values(?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            for (var i : added){
                ps.setString(1, i.getMaPhieu());
                ps.setString(2, i.getMaMay());
                ps.setInt(3, i.getSoLuong());
                ps.setDouble(4, i.getDonGia());
                if (ps.executeUpdate() <= 0){
                    return false;
                }
                if(handleQuantityChange(i.getMaMay(), false, i.getSoLuong())){
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public int getPreviousQuantity(String maPhieu, String maMay){
        var query = "select soLuong from chitietphieuxuat where maPhieu = ? and maMay = ?";
        try {
            PreparedStatement PP = conn.prepareStatement(query);
            PP.setString(1, maPhieu);
            PP.setString(2, maMay);
            rs = PP.executeQuery();
            if (rs.next()) {
                int result= rs.getInt("soLuong");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    public boolean handleDeleted(ArrayList<ChitietPhieuNhapDto> deleted){
        var query = "delete from chitietphieuxuat where maPhieu = ? and maMay = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            for (var i : deleted){
                ps.setString(1, i.getMaPhieu());
                ps.setString(2, i.getMaMay());
                if (ps.executeUpdate() <= 0){
                    return false;
                }
                if(handleQuantityChange(i.getMaMay(), true, i.getSoLuong())){
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean handleUpdated(ArrayList<ChitietPhieuNhapDto> updated){
        var query = "update chitietphieuxuat set soLuong = ? where maPhieu = ? and maMay = ?";
        try {
            PreparedStatement ps= conn.prepareStatement(query);
            for(var i:updated){
                int previousQuantity = getPreviousQuantity(i.getMaPhieu(), i.getMaMay());
                ps.setInt(1, i.getSoLuong());
                ps.setString(2, i.getMaPhieu());
                ps.setString(3, i.getMaMay());
                if(ps.executeUpdate() <= 0){
                    return false;
                }
                int div = Math.abs(i.getSoLuong() - previousQuantity);
                boolean isIncrease = !(i.getSoLuong()>previousQuantity);
                if(handleQuantityChange(i.getMaMay(), isIncrease, div)){
                    return false;
                }
            }
            return true;

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean HandleCtpChange(ArrayList<ChitietPhieuNhapDto> added, ArrayList<ChitietPhieuNhapDto> deleted, ArrayList<ChitietPhieuNhapDto> updated){
        try {
            conn.setAutoCommit(false);
            if(!added.isEmpty()){
                if(!handedAdded(added)){
                    conn.rollback();
                    return false;
                }
            }
            if(!deleted.isEmpty()){
                if(!handleDeleted(deleted)){
                    conn.rollback();
                    return false;
                }
            }
            if(!updated.isEmpty()){
                if(!handleUpdated(updated)){
                    conn.rollback();
                    return false;
                }
            }
            conn.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                conn.setAutoCommit(true);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
    public boolean set_Total(String maphieu,double total){
        var query = "update phieuxuat set tongTien = ? where maPhieu = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setDouble(1, total);
            ps.setString(2, maphieu);
            if(ps.executeUpdate() > 0){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean xoaPhieuXuat(String id){
        var query = "select * from chitietphieuxuat where maPhieu = ?";
        try {
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();
            ArrayList<ChitietPhieuNhapDto> list = new ArrayList<>();
            while (rs.next()){
                ChitietPhieuNhapDto ctpn = new ChitietPhieuNhapDto(rs.getString("maPhieu"), rs.getString("maMay"), rs.getInt("soLuong"), rs.getDouble("donGia"), "placeholder");
                list.add(ctpn);
            }
            if(!handleDeleted(list)){
                conn.rollback();
                return false;
            }
            var query2 = "delete from phieuxuat where maPhieu = ?";
            PreparedStatement ps2 = conn.prepareStatement(query2);
            ps2.setString(1, id);
            if(ps2.executeUpdate() > 0){
                conn.commit();
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                conn.setAutoCommit(true);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
}
