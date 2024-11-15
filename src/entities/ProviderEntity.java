package entities;

public class ProviderEntity {
    String  maNhaCungCap;
    String tenNhaCungCap;
    String Sdt;
    String diaCHi;

    public ProviderEntity(String diaCHi, String maNhaCungCap, String sdt, String tenNhaCungCap) {
        this.diaCHi = diaCHi;
        this.maNhaCungCap = maNhaCungCap;
        Sdt = sdt;
        this.tenNhaCungCap = tenNhaCungCap;
    }

    public String getDiaCHi() {
        return diaCHi;
    }

    public void setDiaCHi(String diaCHi) {
        this.diaCHi = diaCHi;
    }

    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public String getSdt() {
        return Sdt;
    }

    @Override
    public String toString() {
        return "ProviderEntity{" +
                "diaCHi='" + diaCHi + '\'' +
                ", maNhaCungCap='" + maNhaCungCap + '\'' +
                ", teNhaCungCap='" + tenNhaCungCap + '\'' +
                ", Sdt='" + Sdt + '\'' +
                '}';
    }

    public void setSdt(String sdt) {
        Sdt = sdt;
    }

    public String getTeNhaCungCap() {
        return tenNhaCungCap;
    }

    public void setTeNhaCungCap(String teNhaCungCap) {
        this.tenNhaCungCap = teNhaCungCap;
    }
}
