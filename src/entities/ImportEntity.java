package entities;

import dao.ImportDao;

import java.sql.Timestamp;
import java.util.List;

public class ImportEntity {
    String maPhieu;
    String nguoiTao;
    Timestamp thoiGianTao ;
    String maNhaCungCap;
    double tongTien;
    public List<ImportDetailEntity> listPhieuNhap;

    public ImportEntity(String maPhieu, String nguoiTao, Timestamp thoiGianTao,String maNhaCungCap, double tongTien) {
        this.maPhieu = maPhieu;
        this.nguoiTao = nguoiTao;
        this.thoiGianTao = thoiGianTao;
        this.maNhaCungCap=maNhaCungCap;
        this.tongTien = tongTien;
    }

    public String getMaPhieu() {
        ImportDao u = new ImportDao();
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getNguoiTao() {
        return nguoiTao;
    }

    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public void setNguoiTao(String nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    public Timestamp getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(Timestamp thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public double getTotalPrice(){
        double sum = 0;
        for (ImportDetailEntity item: listPhieuNhap){
            sum+= item.getDonGia()*item.getSoLuong();
        }
        return sum;
    }
}
