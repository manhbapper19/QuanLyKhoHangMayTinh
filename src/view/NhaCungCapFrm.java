package view;

import dao.NhaCungCapDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.NhaCungCap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class NhaCungCapFrm extends javax.swing.JPanel implements ActionListener{
    private NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();
    ArrayList<model.NhaCungCap> listNhaCungCap = new ArrayList<>();
    
    public NhaCungCapFrm() {
        initComponents();
        addActionListener();
        addSearchListener();
        setTableNCCData();
        setComboboxData();
        setTableNCCData1();
    }
    
    // hiển thị dữ liệu từ SQL ra table 
    public void setTableNCCData(){
        try {
            listNhaCungCap = nhaCungCapDAO.selectAll();
            DefaultTableModel model = (DefaultTableModel) tbNCC.getModel();
            model.setRowCount(0);
            for(model.NhaCungCap ncc : listNhaCungCap){
                model.addRow(new Object[]{
                    ncc.getMaNhaCungCap(),
                    ncc.getTenNhaCungCap(),
                    ncc.getSdt(),
                    ncc.getDiaChi()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
    
    // lấy dữ liệu từ dòng được chọn trong table 
    private NhaCungCap getSelectedNCC(){
        int selectedRow = tbNCC.getSelectedRow();
        if (selectedRow >= 0) {
            String maNhaCungCap = (String) tbNCC.getValueAt(selectedRow, 0);
            String tenNhaCungCap = (String) tbNCC.getValueAt(selectedRow, 1);
            String sdt = (String) tbNCC.getValueAt(selectedRow, 2);
            String diaChi = (String) tbNCC.getValueAt(selectedRow, 3);
            return new NhaCungCap(maNhaCungCap, tenNhaCungCap, sdt, diaChi);
        }
        return null;
    }
    
    /* hiển thị dữ liệu ra combobox */
    
    // B1 : lấy tên các cột trong table truyền vào list
    private List<String> getColumnNames() {
        List<String> columnNames = new ArrayList<>();
        for (int i = 0; i < tbNCC.getColumnCount(); i++) {
            columnNames.add(tbNCC.getColumnName(i));
        }
        return columnNames;
    }

    // B2 : truyền dữ liệu từ list vào combobox
    private void setComboboxData() {
        List<String> columnNames = getColumnNames();
        jComboBox1.removeAllItems();
        for (String columnName : columnNames) {
            jComboBox1.addItem(columnName);
        }
    }
    
    /* hiển thị dữ liệu ra table dựa vào từ khóa tìm kiếm theo cột */
    
    // B1 : lấy giá trị của cột tương ứng cho 1 nhà cung cấp cụ thể
    private String getColumnValue(NhaCungCap ncc, String columnName) {
        switch (columnName) {
            case "Mã NCC":
                return ncc.getMaNhaCungCap();
            case "Tên NCC":
                return ncc.getTenNhaCungCap();
            case "Số điện thoại":
                return ncc.getSdt();
            case "Địa chỉ":
                return ncc.getDiaChi();
            default:
                return "";
        }
    }
    
    // B2 : lọc dữ liệu thep từ khóa tìm kiếm
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
        List<NhaCungCap> filteredList = new ArrayList<>();

        for (NhaCungCap ncc : listNhaCungCap) {
            String columnValue = getColumnValue(ncc, selectedColumn).toLowerCase(); // lấy giá trị của 1 cột cụ thể từ đối tượng ncc
            if (columnValue.contains(searchText)) {
                filteredList.add(ncc);
            }
        }

        updateTableData(filteredList);
    }
    
    // B3 : cập nhật dữ liệu cho tbNCC
    private void updateTableData(List<NhaCungCap> list) {
        DefaultTableModel model = (DefaultTableModel) tbNCC.getModel();
        model.setRowCount(0);
        for (NhaCungCap ncc : list) {
            model.addRow(new Object[]{
                    ncc.getMaNhaCungCap(), ncc.getTenNhaCungCap(), ncc.getSdt(), ncc.getDiaChi()
            });
        }
    }
    
    //B4 : hiển thị dữ liệu ra table
    private void setTableNCCData1() {
        try {
            listNhaCungCap = nhaCungCapDAO.selectAll();
            updateTableData(listNhaCungCap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbNCC = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnThemNCC = new javax.swing.JButton();
        btnUpdateNCC = new javax.swing.JButton();
        btnDeleteNCC = new javax.swing.JButton();
        btnNhapExcelNCC = new javax.swing.JButton();
        btnXuatExcel = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        txtSearch = new javax.swing.JTextField();
        btnReset = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(240, 240, 240));

        tbNCC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã NCC", "Tên NCC", "Số điện thoại", "Địa chỉ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbNCC);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức năng"));

        btnThemNCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_add_25px_5.png"))); // NOI18N
        btnThemNCC.setText("Thêm");
//        btnThemNCC.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnThemNCCActionPerformed(evt);
//            }
//        });

        btnUpdateNCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_edit_25px.png"))); // NOI18N
        btnUpdateNCC.setText("Sửa");

        btnDeleteNCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_delete_25px_1.png"))); // NOI18N
        btnDeleteNCC.setText("Xóa");

        btnNhapExcelNCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_add_file_25px_2.png"))); // NOI18N
        btnNhapExcelNCC.setText("Nhập Excel");
        btnNhapExcelNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapExcelNCCActionPerformed(evt);
            }
        });

        btnXuatExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-microsoft-excel-2019-25.png"))); // NOI18N
        btnXuatExcel.setText("Xuất Excel");
        btnXuatExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(btnThemNCC)
                .addGap(18, 18, 18)
                .addComponent(btnUpdateNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDeleteNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnNhapExcelNCC)
                .addGap(18, 18, 18)
                .addComponent(btnXuatExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemNCC)
                    .addComponent(btnUpdateNCC)
                    .addComponent(btnDeleteNCC)
                    .addComponent(btnNhapExcelNCC)
                    .addComponent(btnXuatExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_reset_25px_1.png"))); // NOI18N
        btnReset.setText("Làm mới");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(btnReset)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReset)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(1, 1, 1)))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE))
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

    private void btnNhapExcelNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapExcelNCCActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            readExcelFile(selectedFile);
        }
    }//GEN-LAST:event_btnNhapExcelNCCActionPerformed
    
    private void readExcelFile(File file) {
        List<NhaCungCap> existingNCC = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            DefaultTableModel model = (DefaultTableModel) tbNCC.getModel();
            model.setRowCount(0); // Clear existing data

            List<NhaCungCap> newNCC = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);
                NhaCungCap ncc = new NhaCungCap(
                        row.getCell(0).getStringCellValue(),
                        row.getCell(1).getStringCellValue(),
                        row.getCell(2).getStringCellValue(),
                        row.getCell(3).getStringCellValue()
                );

                if(!nhaCungCapDAO.exists(ncc.getMaNhaCungCap())) {
                    nhaCungCapDAO.insert(ncc);
                    newNCC.add(ncc);
                }else {
                    existingNCC.add(ncc);
                }
            }

            for (NhaCungCap ncc : newNCC) {
                model.addRow(new Object[]{
                        ncc.getMaNhaCungCap(),
                        ncc.getTenNhaCungCap(),
                        ncc.getSdt(),
                        ncc.getDiaChi()
                });
            }
            if (!existingNCC.isEmpty()) {
                StringBuilder message = new StringBuilder("Các sản phẩm sau đã có trong SQL:\n");
                for (NhaCungCap ncc : existingNCC) {
                    message.append(ncc.getMaNhaCungCap()).append(" - ").append(ncc.getTenNhaCungCap()).append("\n");
                }
                JOptionPane.showMessageDialog(this, message.toString(), "Existing NhaCungCap", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading Excel file: " + e.getMessage());
        }
    }
    
    private void btnXuatExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExcelActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Excel File");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getAbsolutePath().endsWith(".xlsx")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".xlsx");
            }
            try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                XSSFSheet sheet = workbook.createSheet("Danh sach nha cung cap");
                XSSFRow row = sheet.createRow(0);
                Cell cell = row.createCell(0, CellType.STRING);
                cell.setCellValue("Ma nha cung cap");
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue("Ten nha cung cap");
                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue("So dien thoai");
                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue("Dia chi");
                cell = row.createCell(4, CellType.STRING);
                for (int i = 0; i < listNhaCungCap.size(); i++) {
                    row = sheet.createRow(i + 1);
                    cell = row.createCell(0, CellType.STRING);
                    cell.setCellValue(listNhaCungCap.get(i).getMaNhaCungCap());
                    cell = row.createCell(1, CellType.STRING);
                    cell.setCellValue(listNhaCungCap.get(i).getTenNhaCungCap());
                    cell = row.createCell(2, CellType.NUMERIC);
                    cell.setCellValue(listNhaCungCap.get(i).getSdt());
                    cell = row.createCell(3, CellType.STRING);
                    cell.setCellValue(listNhaCungCap.get(i).getDiaChi());
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
    }//GEN-LAST:event_btnXuatExcelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeleteNCC;
    private javax.swing.JButton btnNhapExcelNCC;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnThemNCC;
    private javax.swing.JButton btnUpdateNCC;
    private javax.swing.JButton btnXuatExcel;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbNCC;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables

    private void addActionListener() {
        btnThemNCC.addActionListener(this);
        btnUpdateNCC.addActionListener(this);
        btnDeleteNCC.addActionListener(this);
        btnReset.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // thực hiện các hành động
        var obj = e.getSource();
        if(obj.equals(btnThemNCC)){
            AddNhaCungCap addNhaCungCap = new AddNhaCungCap((java.awt.Frame) SwingUtilities.getWindowAncestor(this), true, this);
            addNhaCungCap.setVisible(true);
        } else if(obj.equals(btnUpdateNCC)){
            NhaCungCap selectedNCC = getSelectedNCC();
            if (selectedNCC != null) {
                UpdateNhaCungCap updateNhaCungCap = new UpdateNhaCungCap((java.awt.Frame) SwingUtilities.getWindowAncestor(this), true, this, selectedNCC);
                updateNhaCungCap.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Chọn nhà cung cấp cần update!");
                
            }
        } else if(obj.equals(btnDeleteNCC)){
            int selectedRow = tbNCC.getSelectedRow();
            if (selectedRow >= 0) {
                String maNhaCungCap = (String) tbNCC.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn muốn xóa nhà cung cấp này ?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    NhaCungCap ncc = new NhaCungCap();
                    ncc.setMaNhaCungCap(maNhaCungCap);
                    if (nhaCungCapDAO.delete(ncc)) {
                        JOptionPane.showMessageDialog(this, "Nhà cung cấp đã xóa thành công!");
                        setTableNCCData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Xóa nhà cung cấp thất bại!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Chưa chọn nhà cung cấp cần xóa!");
            }
        } else if(obj.equals(btnReset)){
            txtSearch.setText("");
        }
    }

    
}
