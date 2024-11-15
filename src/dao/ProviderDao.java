package dao;

import database.JDBC;
import entities.ProviderEntity;
import view.NhapHang;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProviderDao {
    Connection conn = JDBC.getJDBCConnection();
    PreparedStatement ps;
    ResultSet rs;

    public List<ProviderEntity> getList() throws SQLException {
        String query = "select  * from nhacungcap";
        List<ProviderEntity> providerList = new ArrayList<>();
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                ps = conn.prepareStatement(query);
                ProviderEntity provider = new ProviderEntity(
                        rs.getString("maNhaCungCap"),
                        rs.getString("tenNhaCungCap"),
                        rs.getString("Sdt"),
                        rs.getString("diaCHi")
                );
                providerList.add(provider);
            }
            return providerList;
        } catch (Exception e) {
            throw new SQLException("error getting provider list");
        }
    }

    public boolean updateProvider(ProviderEntity provider) {
        String query = "update nhacungcap set tenNhaCungCap = ?, Sdt = ?, diaCHi = ? where maNhaCungCap = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, provider.getTeNhaCungCap());
            ps.setString(2, provider.getSdt());
            ps.setString(3, provider.getDiaCHi());
            ps.setString(4, provider.getMaNhaCungCap());
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean addProvider(ProviderEntity provider){
        String query = "insert into nhacungcap values(?,?,?,?)";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, provider.getMaNhaCungCap());
            ps.setString(2, provider.getTeNhaCungCap());
            ps.setString(3, provider.getSdt());
            ps.setString(4, provider.getDiaCHi());
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
    }
        return false;
    }
    public static void main(String[] args) {
        ProviderDao providerDao = new ProviderDao();
        try {
            List<ProviderEntity> providerList = providerDao.getList();
            for (ProviderEntity provider : providerList) {
                System.out.println(provider.getMaNhaCungCap());
                System.out.println(provider.getTeNhaCungCap());
                System.out.println(provider.getSdt());
                System.out.println(provider.getDiaCHi());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
