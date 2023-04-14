package com.example.springmvc.entity;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class User {
    private int userId;
    private String userName;
    private int userAge;
    private String userPassword;
    private String userAccount;
    private boolean userBan;

    private String userSalt;


    public void setUser(String userName, int userAge, String userPassword, String userAccount, boolean userBan) {

        this.userName = userName;
        this.userAge = userAge;
        this.userPassword = userPassword;
        this.userAccount = userAccount;
        this.userBan = userBan;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public boolean isBan() {
        return userBan;
    }

    public void setBan(boolean userBan) {
        this.userBan = userBan;
    }

    public boolean isUserBan() {
        return userBan;
    }

    public User setUserBan(boolean userBan) {
        this.userBan = userBan;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userAge=" + userAge +
                ", userPassword='" + userPassword + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", userBan=" + userBan +
                ", userSalt='" + userSalt + '\'' +
                '}';
    }

    public String getUserSalt() {
        return userSalt;
    }

    public User setUserSalt(String userSalt) {
        this.userSalt = userSalt;
        return this;
    }
}
