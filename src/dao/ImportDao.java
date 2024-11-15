package dao;

import database.JDBC;
import entities.ImportDetailEntity;
import entities.ImportEntity;
import entities.ProductEntity;
import model.PhieuNhap;
import view.ChiTietPhieuNhap;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
class DeletedUpdate {
    String maMay = "";
    int soLuong = 0;

    @Override
    public String toString() {
        return "DeletedUpdate{" +
                "maMay='" + maMay + '\'' +
                ", soLuong=" + soLuong +
                '}';
    }
}

public class ImportDao {
    Connection conn = JDBC.getJDBCConnection();
    PreparedStatement ps;
    ResultSet rs;

    /**
     * Tạo ID cho đơn nhập.
     *
     * @return ID mới cho đơn nhập.
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy vấn.
     */
    public String generateId() throws SQLException {
        String query = "select maPhieu from phieunhap where thoiGianTao = (select max(thoiGianTao) from phieunhap)";
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                String id = rs.getString("maPhieu");
                int Numeric = Integer.parseInt(id.substring(2));
                Numeric++;
                return "PN" + Integer.toString(Numeric);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return null;
    }

    /**
     * Lưu đơn nhập.
     *
     * @param ip Đối tượng ImportEntity chứa thông tin đơn nhập.
     * @return true nếu lưu thành công, ngược lại false.
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy vấn.
     */
    private boolean insertImportTrans(ImportEntity ip) throws SQLException {
        String importQuery = "insert into phieunhap values(?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(importQuery)) {
            ps.setString(1, ip.getMaPhieu());
            ps.setTimestamp(2, Timestamp.valueOf(ip.getThoiGianTao().toLocalDateTime().withNano(0)));
            ps.setString(3, ip.getNguoiTao());
            ps.setString(4, ip.getMaNhaCungCap());
            ps.setDouble(5, ip.getTongTien());
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Cập nhật số lượng sản phẩm.
     *
     * @param importDetails Danh sách chi tiết nhập hàng.
     * @return true nếu cập nhật thành công, ngược lại false.
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy vấn.
     */
    private boolean updateProductQuantityTrans(List<ImportDetailEntity> importDetails) throws SQLException {
        String updatequery = "update maytinh set soLuong = maytinh.soLuong +? where  maMay =?";
        try (PreparedStatement ps = conn.prepareStatement(updatequery)) {
            for (ImportDetailEntity i : importDetails) {
                ps.setInt(1, i.getSoLuong());
                ps.setString(2, i.getMaMay());
                ps.addBatch();
            }
            int[] res = ps.executeBatch();
            for (int i : res) {
                if (i <= 0) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Thêm chi tiết nhập hàng.
     *
     * @param importDetails Danh sách chi tiết nhập hàng.
     * @return true nếu thêm thành công, ngược lại false.
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy vấn.
     */
    private boolean insertImportDetailsTrans(List<ImportDetailEntity> importDetails) throws SQLException {
        String importDetailQuery = "insert into chitietphieunhap values(?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(importDetailQuery)) {
            for (ImportDetailEntity detail : importDetails) {
                ps.setString(1, detail.getMaPhieu());
                ps.setString(2, detail.getMaMay());
                ps.setInt(3, detail.getSoLuong());
                ps.setDouble(4, detail.getDonGia());
                ps.addBatch();
            }
            int[] res = ps.executeBatch();
            for (int i : res) {
                if (i <= 0) {
                    return false;
                }
            }
            return true;
        }
    }
    /**
     * Lưu đơn nhập.
     *
     * @param ip Đối tượng ImportEntity chứa thông tin đơn nhập.
     * @return true nếu lưu thành công, ngược lại false.
     */
    public boolean saveImport(ImportEntity ip) {
        try {
            conn.setAutoCommit(false);
            if (!insertImportTrans(ip)) {
                conn.rollback();
                System.out.println("insert phieu nhap failed");
                return false;
            }
            if (!insertImportDetailsTrans(ip.listPhieuNhap)) {
                conn.rollback();
                System.out.println("insert phieu nhap detail failed");
                return false;
            }
            if (!updateProductQuantityTrans(ip.listPhieuNhap)) {
                conn.rollback();
                System.out.println("update product quantity failed");
                return false;
            }
            conn.commit();
            return true;
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackException) {
                System.err.println(e.getMessage());
                throw new RuntimeException("Failed to rollback transaction", rollbackException);
                
            }
            System.err.println("Exception in saveImport: " + e.getMessage());
            e.printStackTrace(); 
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                throw new RuntimeException("Failed to reset auto-commit", e);
            }
        }
    }


    /**
     * Lọc các chi tiết nhập hàng mới thêm.
     *
     * @param pre Danh sách chi tiết nhập hàng trước đó.
     * @param latest Danh sách chi tiết nhập hàng mới nhất.
     * @return Danh sách chi tiết nhập hàng mới.
     */
    private List<ImportDetailEntity> getAdd(List<ImportDetailEntity> pre, List<ImportDetailEntity> latest) {
        Set<ImportDetailEntity> preSet = new HashSet<>(pre);
        Set<ImportDetailEntity> latestSet = new HashSet<>(latest);
        // kiem tra tren item moi xem nhung cai nao khong co o list moi
        List<ImportDetailEntity> res = latestSet.stream().
                filter(i -> !preSet.contains(i))
                .collect(Collectors.toList());
        return res;
    }

    /**
     * Lọc các chi tiết nhập  bị xóa.
     *
     * @param pre Danh sách chi tiết nhập hàng trước đó.
     * @param latest Danh sách chi tiết nhập hàng mới nhất.
     * @return Danh sách chi tiết nhập hàng bị xóa.
     */
    private List<ImportDetailEntity> getDeleted(List<ImportDetailEntity> pre, List<ImportDetailEntity> latest) {
        Set<ImportDetailEntity> preSet = new HashSet<>(pre);
        Set<ImportDetailEntity> latestSet = new HashSet<>(latest);
        // kiem tra tren item cu xem nhung cai nao khong co o list moi
        return preSet.stream()
                .filter(i -> !latestSet.contains(i))
                .map(i -> {
                    i.setSoLuong(-i.getSoLuong());
                    return i;
                })
                .collect(Collectors.toList());
    }

    /**
     * Lọc các chi tiết nhập được cập nhật.
     *
     * @param pre Danh sách chi tiết nhập  trước đó.
     * @param latest Danh sách chi tiết nhập  mới nhất.
     * @return Danh sách chi tiết nhập hàng được cập nhật.
     */
    private List<ImportDetailEntity> getUpdated(List<ImportDetailEntity> pre, List<ImportDetailEntity> latest) {
        Set<ImportDetailEntity> preSet = new HashSet<>(pre);
        Set<ImportDetailEntity> latestSet = new HashSet<>(latest);
        List<ImportDetailEntity> updatedSet = new ArrayList<>();
        for(ImportDetailEntity i : preSet){
            for(ImportDetailEntity u : latestSet){
                if(i.equals(u)){
                    if(i.getSoLuong() != u.getSoLuong()||i.getDonGia()!=u.getDonGia()){
                        updatedSet.add(u);
                    }
                }
            }
        }
        return updatedSet;
    }
    private List<ImportDetailEntity> getDiff(List<ImportDetailEntity> pre, List<ImportDetailEntity> latest) {
        Set<ImportDetailEntity> preSet = new HashSet<>(pre);

        return latest.stream()
                .filter(preSet::contains)
                .map(i -> {
                    ImportDetailEntity preItem = preSet.stream()
                            .filter(p -> p.equals(i))
                            .findFirst()
                            .orElse(null);

                    if (preItem != null && preItem.getSoLuong() != i.getSoLuong()) {
                        ImportDetailEntity diffEntity = new ImportDetailEntity(i.getDonGia(),i.getMaMay(),i.getMaPhieu(),i.getSoLuong());
                        diffEntity.setSoLuong(i.getSoLuong() - preItem.getSoLuong());
                        return diffEntity;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Cập nhật thông tin đơn nhập.
     *
     * @param latest Đối tượng ImportEntity chứa thông tin đơn nhập mới nhất.
     * @return true nếu cập nhật thành công, ngược lại false.
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy vấn.
     */
    private boolean updateImportTrans(ImportEntity latest) throws SQLException {
        String query = "update phieunhap set maNhaCungCap = ? where maPhieu = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, latest.getMaNhaCungCap());
            ps.setString(2, latest.getMaPhieu());
            return ps.executeUpdate() > 0;
        }
    }
    /**
     * Cập nhật chi tiết nhập .
     *
     * @param latest Danh sách chi tiết nhập  mới nhất.
     * @return true nếu cập nhật thành công, ngược lại false.
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy vấn.
     */
    private boolean updateInsertDetailTrans(List<ImportDetailEntity> latest) throws SQLException {
        String query = "update chitietphieunhap set soLuong = ? where maPhieu =? and  maMay= ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            for (ImportDetailEntity i : latest) {
                ps.setInt(1, i.getSoLuong());
                ps.setString(2, i.getMaPhieu());
                ps.setString(3, i.getMaMay());
                ps.addBatch();
            }
            int[] res = ps.executeBatch();
            for (int i : res) {
                if (i <= 0) {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Xóa chi tiết nhập .
     *
     * @param latest Danh sách chi tiết nhập  mới nhất.
     * @return true nếu xóa thành công, ngược lại false.
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy vấn.
     */
    private boolean deleteInsertDetailTrans(List<ImportDetailEntity> latest) throws SQLException {
        String query = "delete from chitietphieunhap where maPhieu= ? and maMay = ? ";
        for (ImportDetailEntity i:latest){
            System.out.println(i);
        }
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            for (ImportDetailEntity i : latest) {
                ps.setString(1, i.getMaPhieu());
                ps.setString(2, i.getMaMay());
                ps.addBatch();
            }
            int[] res = ps.executeBatch();
            for (int i : res) {
                if (i <= 0) {
                    System.out.println(i);
                    return false;

                }
            }
        }
        return true;
    }

    public boolean updateImport(ImportEntity pre, ImportEntity latest) {
        try {
            conn.setAutoCommit(false);
            System.out.println("starting transaction");
            if (!updateImportTrans(latest)) {
                conn.rollback();
                System.out.println("update phieu nhap failed");
                return false;
            }
            List<ImportDetailEntity> add = getAdd(pre.listPhieuNhap, latest.listPhieuNhap);
            List<ImportDetailEntity> deleted = getDeleted(pre.listPhieuNhap, latest.listPhieuNhap);
            List<ImportDetailEntity> updated = getUpdated(pre.listPhieuNhap, latest.listPhieuNhap);
            List<ImportDetailEntity> diff = getDiff(pre.listPhieuNhap, latest.listPhieuNhap);
            System.out.println("add: " + add.size());
            System.out.println("deleted: " + deleted.size());
            System.out.println("updated: " + updated.size());
            System.out.println("diff"+diff.size());
            if (!updateInsertDetailTrans(updated)) {
                conn.rollback();
                return false;
            }
            System.out.println("update insert detail success");
            if (!updateProductQuantityTrans(diff)) {
                conn.rollback();
                return false;
            }
            System.out.println("update product w/updated quantity success");
            if (!insertImportDetailsTrans(add)) {
                conn.rollback();
                return false;
            }
            System.out.println("add insert detail success");
            if (!updateProductQuantityTrans(add)) {
                conn.rollback();
                return false;
            }
            System.out.println("update product w/added quantity success");
            if (!deleteInsertDetailTrans(deleted)) {
                conn.rollback();
                return false;
            }
            System.out.println("deleted detail success");
            if (!updateProductQuantityTrans(deleted)) {
                conn.rollback();
                return false;
            }
            System.out.println("update product  w/deleted quantity success");
            conn.commit();
            return true;
        } catch (Exception e) {
            try {
                System.err.println(e.getMessage());
                conn.rollback();
            } catch (SQLException rollbackException) {

                throw new RuntimeException("Failed to rollback transaction", rollbackException);
            }
            System.err.println("Exception in updateImport: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Failed to reset auto-commit", e);
            }
        }
    }
    public List<DeletedUpdate> getDeleteQuantity(String maPhieu) throws SQLException {
        String selectQuery = "SELECT maMay, soLuong FROM chitietphieunhap WHERE maPhieu = ?";
        String deleteQuery = "DELETE FROM chitietphieunhap WHERE maPhieu = ?";
        List<DeletedUpdate> li = new ArrayList<>();

        try {
            ps = conn.prepareStatement(selectQuery);
            ps.setString(1, maPhieu);
            rs = ps.executeQuery();

            while (rs.next()) {
                DeletedUpdate du = new DeletedUpdate();
                du.maMay = rs.getString("maMay");
                du.soLuong = -rs.getInt("soLuong");
                li.add(du);
            }
            ps = conn.prepareStatement(deleteQuery);
            ps.setString(1, maPhieu);
            ps.executeUpdate();
            for (DeletedUpdate du : li) {
                System.out.println(du);
            }
            return li;

        } catch (SQLException e) {
            System.out.println("Error occurred during deletion process.");
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }
    }

    public boolean DeletedImport(String maPhieu){
        try {
            conn.setAutoCommit(false);
            List<DeletedUpdate> li = getDeleteQuantity(maPhieu);
            for(DeletedUpdate du : li){
                String query = "update maytinh set soLuong = soLuong + ? where maMay = ?";
                try (PreparedStatement ps = conn.prepareStatement(query)) {
                    ps.setInt(1, du.soLuong);
                    ps.setString(2, du.maMay);
                    if(ps.executeUpdate() <= 0){
                        conn.rollback();
                        System.out.println("update product failed");
                        return false;
                    }
                }
            }
            String query = "delete from phieunhap where maPhieu = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, maPhieu);
                if(ps.executeUpdate() <= 0){
                    conn.rollback();
                    return false;
                }
            }
            conn.commit();
            return true;
        }catch (Exception e){
            try {
                conn.rollback();
            } catch (SQLException rollbackException) {
                throw new RuntimeException("Failed to rollback transaction", rollbackException);
            }
            return false;
        }finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Failed to reset auto-commit", e);
            }
        }
    }

    public static void main(String[] args) throws SQLException {
       try {
            ImportDao importDao = new ImportDao();
            ImportEntity ip = new ImportEntity("PN40", "admin", Timestamp.valueOf(LocalDate.now().atStartOfDay()), "ANPHAT", 0);
            ip.listPhieuNhap = new ArrayList<>();
            ip.listPhieuNhap.add(new ImportDetailEntity(13090000, "LP15", "PN40", 10));
            ip.listPhieuNhap.add(new ImportDetailEntity(25190000, "LP12", "PN40", 20));
            ip.listPhieuNhap.add(new ImportDetailEntity(23190000, "LP17", "PN40", 15));
            //System.out.println(importDao.saveImport(ip));
            //test updated
           ImportEntity latest = new ImportEntity("PN40", "admin", Timestamp.valueOf(LocalDate.now().atStartOfDay()), "ANPHAT", 0);
              latest.listPhieuNhap = new ArrayList<>();
                latest.listPhieuNhap.add(new ImportDetailEntity(13090000, "LP15", "PN40", 27));
                latest.listPhieuNhap.add(new ImportDetailEntity(25190000, "LP12", "PN40", 54));
                latest.listPhieuNhap.add(new ImportDetailEntity(23190000, "LP17", "PN40", 22));
            System.out.println(importDao.DeletedImport("PN40"));
            //System.out.println(importDao.updateImport(ip, latest));
           //List<ImportDetailEntity> u = importDao.getUpdated(ip.listPhieuNhap, latest.listPhieuNhap);
           //List<ImportDetailEntity> e = importDao.getDiff(ip.listPhieuNhap, latest.listPhieuNhap);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
