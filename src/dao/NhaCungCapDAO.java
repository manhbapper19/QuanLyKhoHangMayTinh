package dao;

import database.JDBC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.NhaCungCap;

public class NhaCungCapDAO implements DAOInterface<NhaCungCap>{
    Connection conn = JDBC.getJDBCConnection();
    PreparedStatement ps;
    ResultSet rs;
    
    @Override
    public boolean insert(NhaCungCap ncc) {
        String sql = "INSERT INTO nhacungcap values (?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, ncc.getMaNhaCungCap());
            ps.setString(2, ncc.getTenNhaCungCap());
            ps.setString(3, ncc.getSdt());
            ps.setString(4, ncc.getDiaChi());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(NhaCungCap ncc) {
        String sql = "UPDATE nhacungcap SET tenNhaCungCap=?, Sdt=?, diaChi=? WHERE maNhaCungCap=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, ncc.getTenNhaCungCap());
            ps.setString(2, ncc.getSdt());
            ps.setString(3, ncc.getDiaChi());
            ps.setString(4, ncc.getMaNhaCungCap());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(NhaCungCap ncc) {
        String sql = "DELETE FROM nhacungcap WHERE maNhaCungCap=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, ncc.getMaNhaCungCap());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<NhaCungCap> selectAll() {
        ArrayList<NhaCungCap> listNhaCungCap = new ArrayList<>();
        String sql = "SELECT * FROM nhacungcap";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                NhaCungCap ncc = new NhaCungCap();
                ncc.setMaNhaCungCap(rs.getString("maNhaCungCap"));
                ncc.setTenNhaCungCap(rs.getString("tenNhaCungCap"));
                ncc.setSdt(rs.getString("Sdt"));
                ncc.setDiaChi(rs.getString("diaChi"));
                listNhaCungCap.add(ncc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listNhaCungCap;
    }

    @Override
    public NhaCungCap selectById(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
