package dao.Dto;

import model.ChiTietPhieu;

public class ChitietPhieuNhapDto extends ChiTietPhieu {
    //private int Stt;
    private String TenSanPham;

    public ChitietPhieuNhapDto(String maPhieu, String maMay, int soLuong, double donGia, String tenSanPham) {
        super(maPhieu, maMay, soLuong, donGia);
        TenSanPham = tenSanPham;
    }
    public ChitietPhieuNhapDto(ChitietPhieuNhapDto other) {
        super(other.getMaPhieu(), other.getMaMay(), other.getSoLuong(), other.getDonGia());
        this.TenSanPham = other.TenSanPham;
    }
    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        TenSanPham = tenSanPham;
    }
    public ChiTietPhieu downgrade(){
        return new ChiTietPhieu(getMaPhieu(), getMaMay(), getSoLuong(), getDonGia());
    }

}
