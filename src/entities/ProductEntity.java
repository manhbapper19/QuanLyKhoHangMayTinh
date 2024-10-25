package entities;

public class ProductEntity {
    private int congSuatNguon;
    private  String maMay;
    private String tenMay;
    private int soLuong;
    private String ram;
    private String cardManHinh;

    public int getCongSuatNguon() {
        return congSuatNguon;
    }

    public void setCongSuatNguon(int congSuatNguon) {
        this.congSuatNguon = congSuatNguon;
    }

    public ProductEntity(String maMay, String tenMay, int soLuong, String tenCpu, String ram, String cardManHinh, double gia, String mainBoard, Integer congSuatNguon, String loaiMay, String rom, Double kichThuocMan, String dungLuongPin, String xuatXu, Integer trangThai) {
        this.maMay = maMay;
        this.tenMay = tenMay;
        this.soLuong = soLuong;
        this.tenCpu = tenCpu;
        this.ram = ram;
        this.cardManHinh = cardManHinh;
        this.gia = gia;
        this.mainBoard = mainBoard;
        this.congSuatNguon = congSuatNguon;
        this.loaiMay = loaiMay;
        this.rom = rom;
        this.kichThuocMan = kichThuocMan;
        this.dungLuongPin = dungLuongPin;
        this.xuatXu = xuatXu;
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "cardManHinh='" + cardManHinh + '\'' +
                ", maMay='" + maMay + '\'' +
                ", tenMay='" + tenMay + '\'' +
                ", soLuong=" + soLuong +
                ", ram='" + ram + '\'' +
                ", tenCpu='" + tenCpu + '\'' +
                ", mainBoard='" + mainBoard + '\'' +
                ", loaiMay='" + loaiMay + '\'' +
                ", gia='" + gia + '\'' +
                ", kichThuocMan=" + kichThuocMan +
                ", dungLuongPin='" + dungLuongPin + '\'' +
                ", xuatXu='" + xuatXu + '\'' +
                ", trangThai=" + trangThai +
                ", rom='" + rom + '\'' +
                '}';
    }

    private String tenCpu;
    private String mainBoard;//nullable
    private String loaiMay;
    private double gia;
    private double kichThuocMan;
    private String dungLuongPin;

    public String getRom() {
        return rom;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    private String xuatXu;
    private int trangThai;
    private String rom;

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

    public Double getGia() {
        return gia;
    }

    public void setGia(double gia) {
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

}
