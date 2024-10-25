package dao;

import database.JDBC;
import entities.ProductEntity;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductsDao {
    Connection conn = JDBC.getJDBCConnection();
    PreparedStatement ps;
    ResultSet rs;

    public List<ProductEntity> getProductList() throws SQLException {
        String sql = "SELECT * FROM maytinh";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<ProductEntity> productList = new ArrayList<>();
            while (rs.next()) {
                ProductEntity product = new ProductEntity(
                        rs.getString("maMay"),
                        rs.getString("tenMay"),
                        rs.getInt("soLuong"),
                        rs.getString("tenCpu"),
                        rs.getString("ram"),
                        rs.getString("cardManHinh"),
                        rs.getDouble("gia"),
                        rs.getString("mainBoard"),
                        rs.getInt("congSuatNguon"),
                        rs.getString("loaiMay"),
                        rs.getString("rom"),
                        rs.getDouble("kichThuocMan"),
                        rs.getString("dungLuongPin"),
                        rs.getString("xuatXu"),
                        rs.getInt("trangThai")
                );
                productList.add(product);
            }
            return productList;
        } catch (Exception e) {
            throw new SQLException("Error when getting product list");
        }
    }
    private boolean insertProduct(ProductEntity product) throws SQLException {
        String sql = "INSERT INTO maytinh values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, product.getMaMay());
            ps.setString(2, product.getTenMay());
            ps.setInt(3, product.getSoLuong());
            ps.setString(4, product.getTenCpu());
            ps.setString(5, product.getRam());
            ps.setString(6, product.getCardManHinh());
            ps.setDouble(7, product.getGia());
            ps.setString(8, product.getMainBoard());
            ps.setInt(9, product.getCongSuatNguon());
            ps.setString(10, product.getLoaiMay());
            ps.setString(11, product.getRom());
            ps.setObject(12, product.getKichThuocMan());
            ps.setString(13, product.getDungLuongPin());
            ps.setString(14, product.getXuatXu());
            ps.setObject(15, product.getTrangThai());
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (Exception e) {
            throw new SQLException("Error when inserting product");
        }
        return false;
    }
    private  boolean softRemove(ProductEntity product) throws SQLException {
        try {
            String query = "update  maytinh set trangThai = ? where maMay = ?";
            ps.setInt(1, (product.getTrangThai() == 1) ? 0 : 1);
            if (ps.executeUpdate() > 0) {
                return true;
            }
        }catch (Exception e){
            throw new SQLException("Error when soft removing product");
        }
        return false;
    }

    public static void main(String[] args) {
        ProductsDao productsDao = new ProductsDao();
        try {
            List<ProductEntity> productList = productsDao.getProductList();
            for (ProductEntity product : productList) {
                System.out.println(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


