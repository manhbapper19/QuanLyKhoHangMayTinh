package dao;

import database.JDBC;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import model.Account;

public class AccountsDAO implements DAOInterface<Account>{
    Connection conn = JDBC.getJDBCConnection();
    PreparedStatement ps;
    ResultSet rs;
    // INSERT data vao SQL
//    public void insertData(String fullName, String user, String password, String email, String phone){
//        String sql = "INSERT INTO account values (?,?,?,?,?)";
//        try {
//            ps = conn.prepareStatement(sql);
//            ps.setString(1, fullName);
//            ps.setString(2, user);
//            ps.setString(3, password);
//            ps.setString(4, email);
//            ps.setString(5, phone);
//            if(ps.executeUpdate() > 0){
////                JOptionPane.showMessageDialog(null, "Bạn đã đăng ký thành công!");
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(AccountsDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    // kiểm tra xem userName tồn tại hay chưa
    public boolean checkUsername(String user){
        String sql = "SELECT userName from account where userName = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, user);
            rs = ps.executeQuery();
            if(rs.next()) return true;
        } catch (SQLException ex) {
            Logger.getLogger(AccountsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    //chekc mật khẩu
    public boolean checkPassword(String password){
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
        if(password.matches(regex)) return true;
        return false;
    }
    
    // check xác nhận mật khẩu và mật khẩu
    public boolean checkPassword1(String password, String confirmPassword){
        if(password.equals(confirmPassword)) return true;
        return false;
    }

    // check email regexp
    public boolean checkEmail(String email){
        String regex = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)+$";
        if(email.matches(regex)) return true;
        return false;
    }

    // check phone regexp
    public boolean checkPhone(String phone){
        String regex = "0[0-9]{9}";
        if(phone.matches(regex)) return true;
        return false;
    }
    
    // check login
    public String checkLogin(String username) {
        String sql = "SELECT password FROM account WHERE userName = ?";
        try (Connection conn = JDBC.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // check điền đầy đủ thông tin
    public boolean checkRong(JTextField txtUserName, JTextField txtFullName, JPasswordField txtMatkhau, JPasswordField txtXacnhanmk, JTextField txtemail, JTextField txtPhone) {
        if (txtUserName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập username!");
            return false;
        }
        if (txtFullName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập fullname!");
            return false;
        }
        if (txtMatkhau.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập mật khẩu!");
            return false;
        }
        if (txtXacnhanmk.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập xác nhận mật khẩu!");
            return false;
        }
        if (txtemail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập email!");
            return false;
        }
        if (txtPhone.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập phone!");
            return false;
        }
        return true;
    }
    
    // check điền đầy đủ thông tin
    public boolean checkNull(JTextField txtUsername, JPasswordField txtPassword){
        if(txtUsername.getText().equals("") || txtPassword.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!");
            return false;
        }
        return true;
    }
    
    @Override
    public boolean insert(Account acc) {
        String sql = "INSERT INTO account values (?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, acc.getUserName());
            ps.setString(2, acc.getFullName());
            ps.setString(3, acc.getPassword());
            ps.setString(4, acc.getEmail());
            ps.setString(5, acc.getPhone());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Account t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        
    }

    @Override
    public boolean delete(Account t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Account> selectAll() {
        ArrayList<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM account";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Account acc = new Account();
                acc.setUserName(rs.getString("userName"));
                acc.setFullName(rs.getString("fullName"));
                acc.setEmail(rs.getString("email"));
                acc.setPhone(rs.getString("phone"));
                list.add(acc);
            }
        } catch (Exception e){
            
        }
        return list;
    }

    @Override
    public Account selectById(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
