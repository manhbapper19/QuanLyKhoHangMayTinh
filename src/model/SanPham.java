package model;

import java.util.Objects;

public class SanPham {
    private String maMay;
    private String tenMay;
    private int soLuong;
    private double gia;
    private String tenCpu;
    private String ram;
    private String Rom;
    private int trangThai;
    private String loaiMay;
    

    public SanPham() {
        
    }

    public SanPham(String maMay, String tenMay, int soLuong, String tenCpu, String ram, double gia, String loaiMay, String Rom, int trangThai ) {
        this.maMay = maMay;
        this.tenMay = tenMay;
        this.soLuong = soLuong;
        this.tenCpu = tenCpu;
        this.ram = ram;
        this.gia = gia;
        this.loaiMay = loaiMay;
        this.Rom = Rom;
        this.trangThai = trangThai;
    }

    public String getMaMay() {
        return maMay;
    }

    public void setMaMay(String maMay) {
        this.maMay = maMay;
    }

    public String getTenMay() {
        return tenMay;
    }

    public void setTenMay(String tenMay) {
        this.tenMay = tenMay;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getTenCpu() {
        return tenCpu;
    }

    public void setTenCpu(String tenCpu) {
        this.tenCpu = tenCpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getRom() {
        return Rom;
    }

    public void setRom(String Rom) {
        this.Rom = Rom;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getLoaiMay() {
        return loaiMay;
    }

    public void setLoaiMay(String loaiMay) {
        this.loaiMay = loaiMay;
    }

    

//    public void xuatHang(int sl) {
//        this.soLuong -= sl;
//    }
    
    

//    @Override
//    public int hashCode() {
//        int hash = 3;
//        hash = 37 * hash + Objects.hashCode(this.maMay);
//        hash = 37 * hash + Objects.hashCode(this.tenMay);
//        hash = 37 * hash + this.soLuong;
//        hash = 37 * hash + (int) (Double.doubleToLongBits(this.gia) ^ (Double.doubleToLongBits(this.gia) >>> 32));
//        hash = 37 * hash + Objects.hashCode(this.tenCpu);
//        hash = 37 * hash + Objects.hashCode(this.ram);
//        hash = 37 * hash + Objects.hashCode(this.xuatXu);
//        hash = 37 * hash + Objects.hashCode(this.cardManHinh);
//        hash = 37 * hash + Objects.hashCode(this.Rom);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final MayTinh other = (MayTinh) obj;
//        if (this.soLuong != other.soLuong) {
//            return false;
//        }
//        if (Double.doubleToLongBits(this.gia) != Double.doubleToLongBits(other.gia)) {
//            return false;
//        }
//        if (!Objects.equals(this.maMay, other.maMay)) {
//            return false;
//        }
//        if (!Objects.equals(this.tenMay, other.tenMay)) {
//            return false;
//        }
//        if (!Objects.equals(this.tenCpu, other.tenCpu)) {
//            return false;
//        }
//        if (!Objects.equals(this.ram, other.ram)) {
//            return false;
//        }
//        if (!Objects.equals(this.xuatXu, other.xuatXu)) {
//            return false;
//        }
//        if (!Objects.equals(this.cardManHinh, other.cardManHinh)) {
//            return false;
//        }
//        return Objects.equals(this.Rom, other.Rom);
//    }

    @Override
    public String toString() {
        return "SanPham{" + "maMay=" + maMay + ", tenMay=" + tenMay + ", soLuong=" + soLuong + ", gia=" + gia + ", tenCpu=" + tenCpu + ", ram=" + ram + ", Rom=" + Rom + ", trangThai=" + trangThai + ", loaiMay=" + loaiMay + '}';
    }

    
}
