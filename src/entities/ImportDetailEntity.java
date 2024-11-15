package entities;

import java.util.Objects;

public class ImportDetailEntity {
    String maPhieu;
    String maMay;
    int soLuong;
    double donGia;
    public void ImportEntity(){

    }
    public ImportDetailEntity(double donGia, String maMay, String maPhieu, int soLuong) {
        this.donGia = donGia;
        this.maMay = maMay;
        this.maPhieu = maPhieu;
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public String getMaMay() {
        return maMay;
    }

    public void setMaMay(String maMay) {
        this.maMay = maMay;
    }

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    @Override
    public String toString() {
        return "ImportDetailEntity{" +
                "donGia=" + donGia +
                ", maPhieu='" + maPhieu + '\'' +
                ", maMay='" + maMay + '\'' +
                ", soLuong=" + soLuong +
                '}';
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    @Override
    public int hashCode() {
        return Objects.hash(maPhieu, maMay);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImportDetailEntity that = (ImportDetailEntity) o;
        return Objects.equals(maPhieu, that.maPhieu) && Objects.equals(maMay, that.maMay);
    }
}
