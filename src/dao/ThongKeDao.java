package dao;

import dao.Dto.ThongKeSanPhamDto;
import database.JDBC;
import model.Account;
import model.Phieu;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThongKeDao {
    Connection conn = JDBC.getJDBCConnection();
    ResultSet rs;
    public List<ThongKeSanPhamDto> getThongKe(Timestamp start, Timestamp end){
        List<ThongKeSanPhamDto> list = new ArrayList<>();
        var query ="SELECT mt.maMay,mt.tenMay, cpN.SL AS SOLUONGNHAP,cpS.SL AS SOLUONGXUAT\n" +
                "FROM maytinh AS mt\n" +
                "         JOIN (\n" +
                "    SELECT chitietphieuxuat.maMay, SUM(chitietphieuxuat.soLuong) AS SL\n" +
                "    FROM chitietphieuxuat\n" +
                "             JOIN phieuxuat ON chitietphieuxuat.maPhieu = phieuxuat.maPhieu\n" +
                "    WHERE phieuxuat.thoiGianTao BETWEEN ? AND ?\n" +
                "    GROUP BY chitietphieuxuat.maMay\n" +
                ") AS cpS ON mt.maMay = cpS.maMay\n" +
                "JOIN(\n" +
                "    SELECT chitietphieunhap.maMay, SUM(chitietphieunhap.soLuong) AS SL\n" +
                "    FROM chitietphieunhap\n" +
                "             JOIN phieunhap ON chitietphieunhap.maPhieu = phieunhap.maPhieu\n" +
                "    WHERE phieunhap.thoiGianTao BETWEEN ? AND ?\n" +
                "    GROUP BY chitietphieunhap.maMay\n" +
                ") AS cpN ON mt.maMay = cpN.maMay\n";
        try {
            PreparedStatement ps= conn.prepareStatement(query);
            ps.setTimestamp(1,start);
            ps.setTimestamp(2,end);
            ps.setTimestamp(3,start);
            ps.setTimestamp(4,end);
            rs = ps.executeQuery();
            while (rs.next()){
                list.add(new ThongKeSanPhamDto(
                        rs.getInt("SOLUONGXUAT"),
                        rs.getString("maMay"),
                        rs.getInt("SOLUONGNHAP"),
                        rs.getString("tenMay")
                ));
            }
            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Phieu> getPhieu(Timestamp start, Timestamp end){
        var query = "select phieunhap.maPhieu,phieunhap.nguoiTao,phieunhap.thoiGianTao,phieunhap.tongTien from phieunhap\n" +
                "union\n" +
                "select phieuxuat.maPhieu,phieuxuat.nguoiTao,phieuxuat.thoiGianTao,phieuxuat.tongTien from phieuxuat\n" +
                "where phieuxuat.thoiGianTao between ? and ?"+
                "order by thoiGianTao";
        List<Phieu> list = new ArrayList<>();
        try {
            PreparedStatement ps= conn.prepareStatement(query);
            ps.setTimestamp(1,start);
            ps.setTimestamp(2,end);
            rs = ps.executeQuery();
            while (rs.next()){
                list.add(new Phieu(
                        rs.getString("maPhieu"),
                        rs.getTimestamp("thoiGianTao"),
                        rs.getString("nguoiTao"),
                        rs.getDouble("tongTien")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Account> getAccount(){
        var query = "select * from account";
        List<Account> list = new ArrayList<>();
        try {
            PreparedStatement ps= conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                Account u = new Account(
                        rs.getString("userName"),
                        rs.getString("fullName"),
                        "MOCK",
                        rs.getString("email")
                );
                u.setRole(rs.getString("role"));
                u.setStatus(rs.getInt("status"));
                list.add(u);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public HashMap<String, Double> getNhapData() {
        HashMap<String, Double> data = new HashMap<>();
        String query = "SELECT\n" +
                "    CONCAT(MONTH(thoiGianTao), '/', YEAR(thoiGianTao)) as month_year,\n" +
                "    SUM(tongTien) as total\n" +
                "FROM phieunhap\n" +
                "GROUP BY CONCAT(MONTH(thoiGianTao), '/', YEAR(thoiGianTao))";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                data.put(rs.getString("month_year"), rs.getDouble("total"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
    public HashMap<String, Double> getXuatData() {
        HashMap<String, Double> data = new HashMap<>();
        String query = "SELECT\n" +
                "    CONCAT(MONTH(thoiGianTao), '/', YEAR(thoiGianTao)) as month_year,\n" +
                "    SUM(tongTien) as total\n" +
                "FROM phieuxuat\n" +
                "GROUP BY CONCAT(MONTH(thoiGianTao), '/', YEAR(thoiGianTao))";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                data.put(rs.getString("month_year"), rs.getDouble("total"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}
