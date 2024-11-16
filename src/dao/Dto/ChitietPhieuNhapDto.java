package dao.Dto;

import model.ChiTietPhieu;

public class ChitietPhieuNhapDto extends ChiTietPhieu {
    //private int Stt;
    private String TenSanPham;

    public ChitietPhieuNhapDto(String maPhieu, String maMay, int soLuong, double donGia, String tenSanPham) {
        super(maPhieu, maMay, soLuong, donGia);
        TenSanPham = tenSanPham;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        TenSanPham = tenSanPham;
    }
}
