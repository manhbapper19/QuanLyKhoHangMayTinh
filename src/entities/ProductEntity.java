package entities;

public class ProductEntity {
    private  String maMay;
    private String tenMay;
    private int soLuong;
    private String ram;
    private String cardManHinh;

    public ProductEntity(String loaiMay, double kichThuocMan, String gia, String cardManHinh, String dungLuongPin, String mainBoard, String maMay, String ram, int soLuong, String tenCpu, String tenMay, int trangThai, String xuatXu) {
        this.loaiMay = loaiMay;
        this.kichThuocMan = kichThuocMan;
        this.gia = gia;
        this.cardManHinh = cardManHinh;
        this.dungLuongPin = dungLuongPin;
        this.mainBoard = mainBoard;
        this.maMay = maMay;
        this.ram = ram;
        this.soLuong = soLuong;
        this.tenCpu = tenCpu;
        this.tenMay = tenMay;
        this.trangThai = trangThai;
        this.xuatXu = xuatXu;
    }

    private String tenCpu;
    private String mainBoard;//nullable
    private String loaiMay;
    private String gia;
    private double kichThuocMan;
    private String dungLuongPin;
    private String xuatXu;
    private int trangThai;

    public String getDungLuongPin() {
        return dungLuongPin;
    }

    public void setDungLuongPin(String dungLuongPin) {
        this.dungLuongPin = dungLuongPin;
    }

    public String getCardManHinh() {
        return cardManHinh;
    }

    public void setCardManHinh(String cardManHinh) {
        this.cardManHinh = cardManHinh;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public double getKichThuocMan() {
        return kichThuocMan;
    }

    public void setKichThuocMan(double kichThuocMan) {
        this.kichThuocMan = kichThuocMan;
    }

    public String getLoaiMay() {
        return loaiMay;
    }

    public void setLoaiMay(String loaiMay) {
        this.loaiMay = loaiMay;
    }

    public String getMainBoard() {
        return mainBoard;
    }

    public void setMainBoard(String mainBoard) {
        this.mainBoard = mainBoard;
    }

    public String getMaMay() {
        return maMay;
    }

    public void setMaMay(String maMay) {
        this.maMay = maMay;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTenCpu() {
        return tenCpu;
    }

    public void setTenCpu(String tenCpu) {
        this.tenCpu = tenCpu;
    }

    public String getTenMay() {
        return tenMay;
    }

    public void setTenMay(String tenMay) {
        this.tenMay = tenMay;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "dungLuongPin='" + dungLuongPin + '\'' +
                ", cardManHinh='" + cardManHinh + '\'' +
                ", gia='" + gia + '\'' +
                ", kichThuocMan=" + kichThuocMan +
                ", loaiMay='" + loaiMay + '\'' +
                ", mainBoard='" + mainBoard + '\'' +
                ", maMay='" + maMay + '\'' +
                ", ram='" + ram + '\'' +
                ", soLuong=" + soLuong +
                ", tenCpu='" + tenCpu + '\'' +
                ", tenMay='" + tenMay + '\'' +
                ", trangThai=" + trangThai +
                ", xuatXu='" + xuatXu + '\'' +
                '}';
    }
}
