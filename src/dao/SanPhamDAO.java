package dao;

import database.JDBC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.SanPham;

public class SanPhamDAO implements DAOInterface<SanPham>{
    Connection conn = JDBC.getJDBCConnection();
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public boolean insert(SanPham sp) {
        String sql = "INSERT INTO maytinh values (?,?,?,?,?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, sp.getMaMay());
            ps.setString(2, sp.getTenMay());
            ps.setInt(3, sp.getSoLuong());
            ps.setString(4, sp.getTenCpu());
            ps.setString(5, sp.getRam());
            ps.setDouble(6, sp.getGia());
            ps.setString(7, sp.getLoaiMay());
            ps.setString(8, sp.getRom());
            ps.setInt(9, sp.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(SanPham sp) {
        String sql = "UPDATE maytinh SET tenMay=?, soLuong=?, tenCpu=?, ram=?, gia=?, loaiMay=?, rom=? WHERE maMay=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, sp.getTenMay());
            ps.setInt(2, sp.getSoLuong());
            ps.setString(3, sp.getTenCpu());
            ps.setString(4, sp.getRam());
            ps.setDouble(5, sp.getGia());
            ps.setString(6, sp.getLoaiMay());
            ps.setString(7, sp.getRom());
            ps.setString(8, sp.getMaMay());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(SanPham sp) {
        String sql = "DELETE FROM maytinh WHERE maMay=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, sp.getMaMay());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<SanPham> selectAll() {
        ArrayList<SanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM maytinh";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                SanPham sp = new SanPham();
                sp.setMaMay(rs.getString("maMay"));
                sp.setTenMay(rs.getString("tenMay"));
                sp.setSoLuong(rs.getInt("soLuong"));
                sp.setTenCpu(rs.getString("tenCpu"));
                sp.setRam(rs.getString("ram"));
                sp.setGia(rs.getDouble("gia"));
                sp.setLoaiMay(rs.getString("loaiMay"));
                sp.setRom(rs.getString("rom"));
                sp.setTrangThai(rs.getInt("trangThai"));
                list.add(sp);
            }
        } catch (Exception e){
            
        }
        return list;
    }

    @Override
    public SanPham selectById(String t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
//    private  boolean softRemove(ProductEntity product) throws SQLException {
//        try {
//            String query = "update  maytinh set trangThai = ? where maMay = ?";
//            ps.setInt(1, (product.getTrangThai() == 1) ? 0 : 1);
//            if (ps.executeUpdate() > 0) {
//                return true;
//            }
//        }catch (Exception e){
//            throw new SQLException("Error when soft removing product");
//        }
//        return false;
//    }
    
    // lấy dữ liệu từ sql theo loại máy để hiển thị lên combobox
    public List<String> getLoaiMayList() {
        List<String> loaiMayList = new ArrayList<>();
        String sql = "SELECT DISTINCT loaiMay FROM maytinh";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                loaiMayList.add(rs.getString("loaiMay"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loaiMayList;
    }
    
    // INSERT data vao SQL
    public void setDanhMucSanPhamtData(String maMay, String tenMay, int soLuong, double donGia){
        String sql = "INSERT INTO sanpham values (?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, maMay);
            ps.setString(2, tenMay);
            ps.setInt(3, soLuong);
            ps.setDouble(4, donGia);
            if(ps.executeUpdate() > 0){
//                JOptionPane.showMessageDialog(null, "Bạn đã đăng ký thành công!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean exists(String maMay) {
        String query = "SELECT count(*) FROM maytinh WHERE maMay = ?";
        try (Connection conn = JDBC.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, maMay);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
