package model;

import java.util.Objects;

public class NhaCungCap {
    private String maNhaCungCap;
    private String tenNhaCungCap;
    private String sdt;
    private String diaChi;

    public NhaCungCap() {
    }

    public NhaCungCap(String maNhaCungCap, String tenNhaCungCap, String sdt, String diaChi) {
        this.maNhaCungCap = maNhaCungCap;
        this.tenNhaCungCap = tenNhaCungCap;
        this.sdt = sdt;
        this.diaChi = diaChi;
    }

    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public String getTenNhaCungCap() {
        return tenNhaCungCap;
    }

    public void setTenNhaCungCap(String tenNhaCungCap) {
        this.tenNhaCungCap = tenNhaCungCap;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    @Override
    public String toString() {
        return "NhaCungCap{" + "maNhaCungCap=" + maNhaCungCap + ", tenNhaCungCap=" + tenNhaCungCap + ", sdt=" + sdt + ", diaChi=" + diaChi + '}';
    }
}
