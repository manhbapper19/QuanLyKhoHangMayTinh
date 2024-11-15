package dao.Dto;

import entities.ImportDetailEntity;

public class ImportDetailDto extends ImportDetailEntity {
    private int diff;

    public ImportDetailDto(double donGia, String maMay, String maPhieu, int soLuong, int diff) {
        super(donGia, maMay, maPhieu, soLuong);
        this.diff = diff;
    }

    @Override
    public String toString() {
        return "ImportDetailDto{" +
                "diff=" + diff +
                '}';
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }
}
