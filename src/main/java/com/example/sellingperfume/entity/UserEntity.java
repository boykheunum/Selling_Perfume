package com.example.sellingperfume.entity;


import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user")
public class UserEntity extends AbtractEntity {

    @NotBlank
    @Column(name = "Full_name", columnDefinition = "varchar(50)")
    private String fullName;

    @Email(message = "Email is not validate")
    @Column(name = "Email", columnDefinition = "varchar(250)", unique = true)
    private String email;

    @NotBlank
    @Column(name = "Username", columnDefinition = "varchar(50)",unique = true)
    private String username;

    @NotBlank
    @Column(name = "Password", columnDefinition = "varchar(255)")
    private String password;

    @Column(name = "Adress", columnDefinition = "varchar(50)")
    private String adress;

    @Column(name = "Phone", columnDefinition = "varchar(50)",unique = true)
    private String phone;

    @Column(name = "Serect_key", columnDefinition = "varchar(50)")
    private String serectKey;

    @Column(name = "Active_OTP", columnDefinition = "boolean default 0")
    private boolean active_otp;

    @Column(name = "Type_acount", columnDefinition = "int default 1")
    private int typeAcount;

    @Column(name = "Avatar", columnDefinition = "varchar(50)")
    private String avatar;

    @Column(name = "Status_acount", columnDefinition = "varchar(50)")
    private String statusAcount;

    @Column(name = "Birthday", columnDefinition = "DATE")
    private String birthday;

    @Column(name = "role", columnDefinition = "varchar(50) default 'USER_ROLE'")
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSerectKey() {
        return serectKey;
    }

    public void setSerectKey(String serectKey) {
        this.serectKey = serectKey;
    }

    public boolean getActive_otp() {
        return active_otp;
    }

    public void setActive_otp(boolean active_otp) {
        this.active_otp = active_otp;
    }

    public int getTypeAcount() {
        return typeAcount;
    }

    public void setTypeAcount(int typeAcount) {
        this.typeAcount = typeAcount;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStatusAcount() {
        return statusAcount;
    }

    public void setStatusAcount(String statusAcount) {
        this.statusAcount = statusAcount;
    }


}
