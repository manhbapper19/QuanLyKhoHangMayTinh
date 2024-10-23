/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Objects;

public class Account {

    private String fullName;
    private String user;
    private String password;
    private String email;
    private String phone;
    public Account() {
        super();
    }

    public Account(String fullName, String user, String password, String email, String phone) {
        this.fullName = fullName;
        this.user = user;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    

//    @Override
//    public int hashCode() {
//        int hash = 3;
//        hash = 47 * hash + Objects.hashCode(this.fullName);
//        hash = 47 * hash + Objects.hashCode(this.user);
//        hash = 47 * hash + Objects.hashCode(this.password);
//        hash = 47 * hash + Objects.hashCode(this.role);
//        hash = 47 * hash + this.status;
//        hash = 47 * hash + Objects.hashCode(this.email);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final Account other = (Account) obj;
//        if (this.status != other.status) {
//            return false;
//        }
//        if (!Objects.equals(this.fullName, other.fullName)) {
//            return false;
//        }
//        if (!Objects.equals(this.user, other.user)) {
//            return false;
//        }
//        if (!Objects.equals(this.password, other.password)) {
//            return false;
//        }
//        if (!Objects.equals(this.role, other.role)) {
//            return false;
//        }
//        return Objects.equals(this.email, other.email);
//    }

    @Override
    public String toString() {
        return "Account{" + "fullName=" + fullName + ", user=" + user + ", password=" + password + ", email=" + email + ", phone=" + phone + '}';
    }

    

    
}
