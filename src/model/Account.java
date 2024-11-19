package model;

import java.util.Objects;

public class Account {
    private String userName;
    private String fullName;
    private String password;
    private String email;
    //private String phone;
    private String role;
    private int Status;
    public Account() {
        super();
    }

    public Account(String userName, String fullName, String password, String email) {
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        //this.phone = phone;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }

    @Override
    public String toString() {
        return "Account{" + "userName=" + userName + ", fullName=" + fullName + ", password=" + password + ", email=" + email +'}';
    }
    
}
