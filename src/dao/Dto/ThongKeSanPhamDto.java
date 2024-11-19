package dao.Dto;

public class ThongKeSanPhamDto{
    private String maSanPham;
    private String tenSanPham;
    private int soLuongNhap;
    private int soLuongXuat;

    public ThongKeSanPhamDto(int getSoLuongNhap, String maSanPham, int soLuongNhap, String tenSanPham) {
        this.soLuongXuat = getSoLuongNhap;
        this.maSanPham = maSanPham;
        this.soLuongNhap = soLuongNhap;
        this.tenSanPham = tenSanPham;
    }

    public int getSoLuongXuat() {
        return soLuongXuat;
    }

    public void setSoLuongXuat(int soLuongXuat) {
        this.soLuongXuat = soLuongXuat;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }
}
