package dao;

import dao.Dto.ChitietPhieuNhapDto;
import database.JDBC;
import model.ChiTietPhieu;
import model.PhieuNhap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
//add note
public class PhieuNhapDAO {
    Connection conn = JDBC.getJDBCConnection();
    PreparedStatement ps;
    ResultSet rs;

    public String taoMaPhieu() {
        var query = "select maPhieu from phieunhap where thoiGianTao = (select max(thoiGianTao) from phieunhap)";
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                String id = rs.getString("maPhieu");
                int Numeric = Integer.parseInt(id.substring(2));
                Numeric++;
                return "PN" + Integer.toString(Numeric);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean SaveCtPn(ArrayList<ChiTietPhieu> list) {
        try {
            var query = "insert into chitietphieunhap values(?,?,?,?)";
            var pdquery = "update maytinh set soLuong = soLuong + ? where maMay = ?";
            ps = conn.prepareStatement(query);
            for (var i : list) {
                ps.setString(1, i.getMaPhieu());
                ps.setString(2, i.getMaMay());
                ps.setInt(3, i.getSoLuong());
                ps.setDouble(4, i.getDonGia());
                if (ps.executeUpdate() > 0) {
                    PreparedStatement psUpdate = conn.prepareStatement(pdquery);
                    psUpdate.setInt(1, i.getSoLuong());
                    psUpdate.setString(2, i.getMaMay());
                    if (psUpdate.executeUpdate() <= 0) {
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

    public Boolean AddPhieuNhap(PhieuNhap pn) {
        try {
            conn.setAutoCommit(false);
            var query = "insert into phieunhap values(?,?,?,?,?)";
            ps = conn.prepareStatement(query);
            ps.setString(1, pn.getMaPhieu());
            ps.setTimestamp(2, pn.getThoiGianTao());
            ps.setString(3, pn.getNguoiTao());
            ps.setString(4, pn.getNhaCungCap());
            ps.setDouble(5, pn.getTongTien());
            if (ps.executeUpdate() > 0) {
                if (SaveCtPn(pn.getCTPhieu())) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public ArrayList<ChitietPhieuNhapDto> getListCtpn(String id){
        var query = " select chitietphieunhap.*, maytinh.tenMay from chitietphieunhap\n" +
                " join maytinh on chitietphieunhap.maMay = maytinh.maMay\n" +
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
    public PhieuNhap findById(String id) {
        var query = "select * from phieunhap where maPhieu = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                PhieuNhap pn = new PhieuNhap();
                pn.setMaPhieu(rs.getString("maPhieu"));
                pn.setThoiGianTao(rs.getTimestamp("thoiGianTao"));
                pn.setNguoiTao(rs.getString("nguoiTao"));
                pn.setNhaCungCap(rs.getString("maNhaCungCap"));
                pn.setTongTien(rs.getDouble("tongTien"));
                return pn;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<PhieuNhap> getList(Timestamp START, Timestamp END, double MIN, double MAX){
        ArrayList<PhieuNhap> list = new ArrayList<>();
        var query = "select * from phieunhap where phieunhap.thoiGianTao between ? and ? and tongTien between ? and ? order by thoiGianTao desc";
        try {
            ps = conn.prepareStatement(query);
            ps.setTimestamp(1, START);
            ps.setTimestamp(2, END);
            ps.setDouble(3, MIN);
            ps.setDouble(4, MAX);
            rs = ps.executeQuery();
            while (rs.next()){
                PhieuNhap pn = new PhieuNhap();
                pn.setMaPhieu(rs.getString("maPhieu"));
                pn.setThoiGianTao(rs.getTimestamp("thoiGianTao"));
                pn.setNguoiTao(rs.getString("nguoiTao"));
                pn.setNhaCungCap(rs.getString("maNhaCungCap"));
                pn.setTongTien(rs.getDouble("tongTien"));
                list.add(pn);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            }
        return null;
    }
    public boolean handleQuantityChange(String id, boolean isIncrease,int quantity){
        var query = "update maytinh set soLuong = soLuong + ? where maMay = ?";
        var check = "select soLuong from maytinh where maMay = ?";
        try {
            ps = conn.prepareStatement(check);
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
        var query = "insert into chitietphieunhap values(?,?,?,?)";
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
    public int getPreviousQuantity(String maPhieu, String maMay){
        var query = "select soLuong from chitietphieunhap where maPhieu = ? and maMay = ?";
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
        var query = "delete from chitietphieunhap where maPhieu = ? and maMay = ?";
        try {
            ps = conn.prepareStatement(query);
            for (var i : deleted){
                ps.setString(1, i.getMaPhieu());
                ps.setString(2, i.getMaMay());
                if (ps.executeUpdate() <= 0){
                    //ahh
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
    public boolean handleUpdated(ArrayList<ChitietPhieuNhapDto> updated){
        var query = "update chitietphieunhap set soLuong = ? where maPhieu = ? and maMay = ?";
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
                boolean isIncrease = i.getSoLuong()>previousQuantity;
                if(handleQuantityChange(i.getMaMay(), isIncrease, div)){
                    return false;
                }
            }
            ps.close();
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
        var query = "update phieunhap set tongTien = ? where maPhieu = ?";
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
    public boolean xoaPhieuNhap(String id){
        var query = "select * from chitietphieunhap where maPhieu = ?";
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
            var query2 = "delete from phieunhap where maPhieu = ?";
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