package dao;

import dao.Dto.UserDetail;
import database.JDBC;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import  controller.BCrypt;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    
    //check userName
    public boolean checkUserName(String user){
        String regex = "^[a-zA-Z0-9]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(user);
        return matcher.matches();
    }
    
    //chekc mật khẩu
    public boolean checkPassword(String password){
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{5,}$";
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
    public boolean checkLogin(String userName, String password){
        String sql = "SELECT * from account where userName = ? and password = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if(rs.next()) return true;
        } catch (SQLException ex) {
            Logger.getLogger(AccountsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public UserDetail login(String userName, String password) {
        String sql = "SELECT * from account where userName = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, userName);
            rs = ps.executeQuery();
            if (rs.next()) {
                 UserDetail u = new UserDetail(rs.getString("fullName"), rs.getString("userName"), rs.getString("role"), rs.getString("email"));
            }
            if(BCrypt.checkpw(password, rs.getString("password"))){
                return new UserDetail(rs.getString("fullName"), rs.getString("userName"), rs.getString("role"), rs.getString("email"));
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(AccountsDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        String sql = "INSERT INTO account values (?,?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, acc.getUserName());
            ps.setString(2, acc.getFullName());
            ps.setString(3, acc.getPassword());
            ps.setString(4, acc.getEmail());
            ps.setString(5, "Nhân viên");
            ps.setInt(6,1);
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
                //acc.setPhone(rs.getString("phone"));
                list.add(acc);
            }
        } catch (Exception e){
            
        }
        return list;
    }

        public boolean checkCurrentPassword(String userName, String currentPassword) {
        String sql = "SELECT * from account where userName = ?";
        try  {
            ps = conn.prepareStatement(sql);
            ps.setString(1, userName);
            rs = ps.executeQuery();
//            if (rs.next()) {
//                String storedPassword = rs.getString("password");
//                return BCrypt.checkpw(currentPassword, storedPassword);
//            }
            if (rs.next()) {
                return BCrypt.checkpw(currentPassword, rs.getString("password"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
        
    @Override
    public Account selectById(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
        public boolean updateAccount(String userName, String fullName, String email, String hashedPassword) {
        String sql = "UPDATE account SET fullName = ?, email = ?, password = ? WHERE userName = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fullName);
            pstmt.setString(2, email);
            pstmt.setString(3, hashedPassword);
            pstmt.setString(4, userName); // Assuming userName is the primary key
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
     public String generateResetKey(){
         SecureRandom ran = new SecureRandom();
         int code = 100000 + ran.nextInt(900000); // Generate a number between 100000 and 999999
         return String.valueOf(code);
     }
     public void emailSender(){
        String host ="smtp.gmail.com" ;
        String port = "587";
        String mailFrom = "manhbapper190704@gmail.com";
        String password = "your-password";
     }
}
