package view;

import dao.SanPhamDAO;
import model.SanPham;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SanPhamFrm extends javax.swing.JPanel implements ActionListener{
    private SanPhamDAO sanPhamDAO = new SanPhamDAO();
    ArrayList<model.SanPham> listSanPham = new ArrayList<>();
    /**
     * Creates new form SanPham
     */
    public SanPhamFrm() {
        initComponents();
        addActionListener();
        setTableSanPhamData();
        populateColumnNamesComboBox();
        addSearchListener();
        setTableSanPhamData1();
    }

    // hiển thị dữ liệu từ SQL ra table
    public void setTableSanPhamData(){
        try {
            listSanPham = sanPhamDAO.selectAll();
            DefaultTableModel model = (DefaultTableModel)tbSanPham.getModel();
            model.setRowCount(0);
            for (model.SanPham sp : listSanPham) {
                model.addRow(new Object[]{
                        sp.getMaMay(), sp.getTenMay(), sp.getSoLuong(), sp.getTenCpu(),
                        sp.getRam(), sp.getGia(), sp.getLoaiMay(), sp.getRom()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    // lấy dữ liệu 
    private SanPham getSelectedProduct() {
        int selectedRow = tbSanPham.getSelectedRow();
        if (selectedRow >= 0) {
            String maMay = (String) tbSanPham.getValueAt(selectedRow, 0);
            String tenMay = (String) tbSanPham.getValueAt(selectedRow, 1);
            int soLuong = (Integer) tbSanPham.getValueAt(selectedRow, 2);
            String tenCpu = (String) tbSanPham.getValueAt(selectedRow, 3);
            String ram = (String) tbSanPham.getValueAt(selectedRow, 4);
            double gia = (Double) tbSanPham.getValueAt(selectedRow, 5);
            String loaiMay = (String) tbSanPham.getValueAt(selectedRow, 6);
            String rom = (String) tbSanPham.getValueAt(selectedRow, 7);
            return new SanPham(maMay, tenMay, soLuong, tenCpu, ram, gia, loaiMay, rom, 1);
        }
        return null;
    }

    private List<String> getColumnNames() {
        List<String> columnNames = new ArrayList<>();
        for (int i = 0; i < tbSanPham.getColumnCount(); i++) {
            columnNames.add(tbSanPham.getColumnName(i));
        }
        return columnNames;
    }

    private void populateColumnNamesComboBox() {
        List<String> columnNames = getColumnNames();
        jComboBox1.removeAllItems();
        for (String columnName : columnNames) {
            jComboBox1.addItem(columnName);
        }
    }
    
    private void setTableSanPhamData1() {
        try {
            listSanPham = sanPhamDAO.selectAll();
            updateTableData(listSanPham);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateTableData(List<SanPham> list) {
        DefaultTableModel model = (DefaultTableModel) tbSanPham.getModel();
        model.setRowCount(0);
        for (SanPham sp : list) {
            model.addRow(new Object[]{
                    sp.getMaMay(), sp.getTenMay(), sp.getSoLuong(), sp.getTenCpu(),
                    sp.getRam(), sp.getGia(), sp.getLoaiMay(), sp.getRom()
            });
        }
    }

    private void addSearchListener() {
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTableData();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTableData();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTableData();
            }
        });
    }

    private void filterTableData() {
        String searchText = txtSearch.getText().toLowerCase(); // lấy dữ liệu tìm kiếm từ txtSearch 
        String selectedColumn = (String) jComboBox1.getSelectedItem(); // lấy giá trị đã chọn trong jcombobox1
        List<SanPham> filteredList = new ArrayList<>();

        for (SanPham sp : listSanPham) {
            String columnValue = getColumnValue(sp, selectedColumn).toLowerCase(); // lấy giá trị của 1 cột cụ thể từ đối tượng sp
            if (columnValue.contains(searchText)) {
                filteredList.add(sp);
            }
        }

        updateTableData(filteredList);
    }
    
    // lấy giá trị của cột tương ứng cho 1 sản phẩm cụ thể
    private String getColumnValue(SanPham sp, String columnName) {
        switch (columnName) {
            case "Mã máy":
                return sp.getMaMay();
            case "Tên máy":
                return sp.getTenMay();
            case "Số lượng":
                return String.valueOf(sp.getSoLuong());
            case "Tên CPU":
                return sp.getTenCpu();
            case "RAM":
                return sp.getRam();
            case "Gía":
                return String.valueOf(sp.getGia());
            case "Loại máy":
                return sp.getLoaiMay();
            case "ROM":
                return sp.getRom();
            default:
                return "";
        }
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnThemSP = new javax.swing.JButton();
        btnSuaSP = new javax.swing.JButton();
        btnXoaSP = new javax.swing.JButton();
        btnNhapExcelSP = new javax.swing.JButton();
        btnXuatExcelSP = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        txtSearch = new javax.swing.JTextField();
        btnReset = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbSanPham = new javax.swing.JTable();

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức năng"));

        btnThemSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_add_25px_5.png"))); // NOI18N
        btnThemSP.setText("Thêm");
//        btnThemSP.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnThemSPActionPerformed(evt);
//            }
//        });

        btnSuaSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_edit_25px.png"))); // NOI18N
        btnSuaSP.setText("Sửa");

        btnXoaSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_delete_25px_1.png"))); // NOI18N
        btnXoaSP.setText("Xóa");
//        btnXoaSP.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnXoaSPActionPerformed(evt);
//            }
//        });

        btnNhapExcelSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_add_file_25px_2.png"))); // NOI18N
        btnNhapExcelSP.setText("Nhập Excel");
        btnNhapExcelSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapExcelSPActionPerformed(evt);
            }
        });

        btnXuatExcelSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-microsoft-excel-2019-25.png"))); // NOI18N
        btnXuatExcelSP.setText("Xuất Excel");
        btnXuatExcelSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatExcelSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnThemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnSuaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(btnXoaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnNhapExcelSP)
                .addGap(20, 20, 20)
                .addComponent(btnXuatExcelSP)
                .addGap(20, 20, 20))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemSP)
                    .addComponent(btnSuaSP)
                    .addComponent(btnXoaSP)
                    .addComponent(btnNhapExcelSP)
                    .addComponent(btnXuatExcelSP))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_reset_25px_1.png"))); // NOI18N
        btnReset.setText("Làm mới");
//        btnReset.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnResetActionPerformed(evt);
//            }
//        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btnReset)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReset)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );

        tbSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã máy", "Tên máy", "Số lượng", "Tên CPU", "RAM", "Gía", "Loại máy", "ROM"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbSanPham);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnXuatExcelSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExcelSPActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Excel File");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getAbsolutePath().endsWith(".xlsx")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".xlsx");
            }
            try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                XSSFSheet sheet = workbook.createSheet("Danh sach san pham");
                XSSFRow row = sheet.createRow(0);
                Cell cell = row.createCell(0, CellType.STRING);
                cell.setCellValue("Ma may");
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue("Ten may");
                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue("So luong");
                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue("Ten CPU");
                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue("RAM");
                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue("Gia");
                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue("Loai may");
                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue("ROM");
                cell = row.createCell(8, CellType.STRING);
                cell.setCellValue("Trang thai");

                for (int i = 0; i < listSanPham.size(); i++) {
                    row = sheet.createRow(i + 1);
                    cell = row.createCell(0, CellType.STRING);
                    cell.setCellValue(listSanPham.get(i).getMaMay());
                    cell = row.createCell(1, CellType.STRING);
                    cell.setCellValue(listSanPham.get(i).getTenMay());
                    cell = row.createCell(2, CellType.NUMERIC);
                    cell.setCellValue(listSanPham.get(i).getSoLuong());
                    cell = row.createCell(3, CellType.STRING);
                    cell.setCellValue(listSanPham.get(i).getTenCpu());
                    cell = row.createCell(4, CellType.STRING);
                    cell.setCellValue(listSanPham.get(i).getRam());
                    cell = row.createCell(5, CellType.NUMERIC);
                    cell.setCellValue(listSanPham.get(i).getGia());
                    cell = row.createCell(6, CellType.STRING);
                    cell.setCellValue(listSanPham.get(i).getLoaiMay());
                    cell = row.createCell(7, CellType.STRING);
                    cell.setCellValue(listSanPham.get(i).getRom());
                    cell = row.createCell(8, CellType.STRING);
                    cell.setCellValue(listSanPham.get(i).getTrangThai());
                }

                try (FileOutputStream out = new FileOutputStream(fileToSave)) {
                    workbook.write(out);
                }

                JOptionPane.showMessageDialog(this, "Xuất Excel thành công!");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error writing Excel file: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnXuatExcelSPActionPerformed

    private void btnNhapExcelSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapExcelSPActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            readExcelFile(selectedFile);
    }
    }//GEN-LAST:event_btnNhapExcelSPActionPerformed
    
    private void readExcelFile(File file) {
        List<SanPham> existingProducts = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            DefaultTableModel model = (DefaultTableModel) tbSanPham.getModel();
            model.setRowCount(0); // Clear existing data

            List<SanPham> newProducts = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);
                SanPham sp = new SanPham(
                    row.getCell(0).getStringCellValue(),
                    row.getCell(1).getStringCellValue(),
                    (int) row.getCell(2).getNumericCellValue(),
                    row.getCell(3).getStringCellValue(),
                    row.getCell(4).getStringCellValue(),
                    row.getCell(5).getNumericCellValue(),
                    row.getCell(6).getStringCellValue(),
                    row.getCell(7).getStringCellValue(),
                    1
                );

                if (!sanPhamDAO.exists(sp.getMaMay())) {
                    sanPhamDAO.insert(sp);
                    newProducts.add(sp);
                }else {
                    existingProducts.add(sp);
                }
            }

            for (SanPham sp : newProducts) {
                model.addRow(new Object[]{
                    sp.getMaMay(), sp.getTenMay(), sp.getSoLuong(), sp.getTenCpu(),
                    sp.getRam(), sp.getGia(), sp.getLoaiMay(), sp.getRom()
                });
            }
            if (!existingProducts.isEmpty()) {
                StringBuilder message = new StringBuilder("Các sản phẩm sau đã có trong SQL:\n");
                for (SanPham sp : existingProducts) {
                    message.append(sp.getMaMay()).append(" - ").append(sp.getTenMay()).append("\n");
                }
                JOptionPane.showMessageDialog(this, message.toString(), "Existing Products", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading Excel file: " + e.getMessage());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNhapExcelSP;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSuaSP;
    private javax.swing.JButton btnThemSP;
    private javax.swing.JButton btnXoaSP;
    private javax.swing.JButton btnXuatExcelSP;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbSanPham;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables

    private void addActionListener() {
        btnThemSP.addActionListener(this);
        btnSuaSP.addActionListener(this);
        btnXoaSP.addActionListener(this);
        btnReset.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // thực hiện các hành động
        var obj = e.getSource();
        if(obj.equals(btnThemSP)){
            AddSanPham addSanPham = new AddSanPham((java.awt.Frame) SwingUtilities.getWindowAncestor(this), true, this);
            addSanPham.setVisible(true);
        } else if(obj.equals(btnSuaSP)){
            SanPham selectedProduct = getSelectedProduct();
            if (selectedProduct != null) {
                UpdateSanPham updateSanPham = new UpdateSanPham((java.awt.Frame) SwingUtilities.getWindowAncestor(this), true, this, selectedProduct);
                updateSanPham.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Chọn sản phẩm cần update!");
                
            }
        } else if(obj.equals(btnXoaSP)){
            int selectedRow = tbSanPham.getSelectedRow();
            if (selectedRow >= 0) {
                String maMay = (String) tbSanPham.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn muốn xóa sản phẩm này ?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    SanPham sp = new SanPham();
                    sp.setMaMay(maMay);
                    if (sanPhamDAO.delete(sp)) {
                        JOptionPane.showMessageDialog(this, "Sản phẩm đã xóa thành công!");
                        setTableSanPhamData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Xóa sản phẩm thất bại!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Chưa chọn sản phẩm cần xóa!");
            }
        } else if(obj.equals(btnReset)){
            txtSearch.setText("");
        }
    }
}
