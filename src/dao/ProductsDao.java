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

    private List<ProductEntity> getProductList() throws SQLException {
        String sql = "SELECT * FROM maytinh";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<ProductEntity> productList = new ArrayList<>();
            while (rs.next()) {
                ProductEntity product = new ProductEntity(
                        rs.getString("loaiMay"),
                        rs.getDouble("kichThuocMan"),
                        rs.getString("gia"),
                        rs.getString("cardManHinh"),
                        rs.getString("dungLuongPin"),
                        rs.getString("mainBoard"),
                        rs.getString("maMay"),
                        rs.getString("ram"),
                        rs.getInt("SoLuong"),
                        rs.getString("tenCpu"),
                        rs.getString("tenMay"),
                        rs.getInt("trangThai"),
                        rs.getString("xuatXu")
                );
                productList.add(product);
            }
            return productList;
        } catch (Exception e) {
            throw new SQLException("Error when getting product list");
        }
    }
    private boolean insertProduct(ProductEntity product) throws SQLException {
        String sql = "INSERT INTO maytinh values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, product.getLoaiMay());
            ps.setDouble(2, product.getKichThuocMan());
            ps.setString(3, product.getGia());
            ps.setString(4, product.getCardManHinh());
            ps.setString(5, product.getDungLuongPin());
            ps.setString(6, product.getMainBoard());
            ps.setString(7, product.getMaMay());
            ps.setString(8, product.getRam());
            ps.setInt(9, product.getSoLuong());
            ps.setString(10, product.getTenCpu());
            ps.setString(11, product.getTenMay());
            ps.setInt(12, product.getTrangThai());
            ps.setString(13, product.getXuatXu());
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


