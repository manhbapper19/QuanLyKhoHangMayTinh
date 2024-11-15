package dao;

import database.JDBC;
import model.ChiTietPhieu;
import model.PhieuNhap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
                    ps = conn.prepareStatement(pdquery);
                    ps.setInt(1, i.getSoLuong());
                    ps.setString(2, i.getMaMay());
                    if (ps.executeUpdate() <= 0) {
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
}