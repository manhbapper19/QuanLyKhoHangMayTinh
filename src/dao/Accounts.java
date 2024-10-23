/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import database.JDBC;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author ASUS
 */
public class Accounts {
    Connection conn = JDBC.getJDBCConnection();
    PreparedStatement ps;
    ResultSet rs;
    // INSERT data vao SQL
    public void insertData(String fullName, String user, String password, String email, String phone){
        String sql = "INSERT INTO account values (?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, fullName);
            ps.setString(2, user);
            ps.setString(3, password);
            ps.setString(4, email);
            ps.setString(5, phone);
            if(ps.executeUpdate() > 0){
//                JOptionPane.showMessageDialog(null, "Bạn đã đăng ký thành công!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Accounts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // kiểm tra xem userName tồn tại hay chưa
    public boolean checkUsername(String user){
        String sql = "SELECT userName from account where userName = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, user);
            rs = ps.executeQuery();
            if(rs.next()) return true;
        } catch (SQLException ex) {
            Logger.getLogger(Accounts.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // check xác nhận mật khẩu và mật khẩu
    public boolean checkPassword(String password, String confirmPassword){
        if(password.equals(confirmPassword)) return true;
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
            Logger.getLogger(Accounts.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //chekc mật khẩu
    public boolean checkPassword(String password){
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
        if(password.matches(regex)) return true;
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
}
