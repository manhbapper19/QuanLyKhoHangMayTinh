package dao;

import database.JDBC;
import model.ChiTietPhieu;
import model.PhieuXuat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String query = "insert into phieuxuat values(?,?,?,?)";
        try {
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(query);
            ps.setString(1, px.getMaPhieu());
            ps.setTimestamp(2, px.getThoiGianTao());
            ps.setString(3, px.getNguoiTao());
            ps.setDouble(4, px.getTongTien());
            if(ps.executeUpdate() > 0 && SaveCtPn(px.getCTPhieu())) {
                conn.commit();
                return true;
            }
            conn.rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean SaveCtPn(ArrayList<ChiTietPhieu> list) {
        try {
            var query = "insert into chitietphieuxuat values(?,?,?,?)";
            var pdquery = "update maytinh set soLuong = soLuong - ? where maMay = ?";
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
}
