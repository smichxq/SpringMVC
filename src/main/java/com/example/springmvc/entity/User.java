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

    private String userActivityCode;


    private boolean userStatus;


    public void setUser(String userName, int userAge, String userPassword, String userAccount, boolean userBan, String userActivityCode, boolean userStatus) {
//        this.userId = userId;
        this.userName = userName;
        this.userAge = userAge;
        this.userPassword = userPassword;
        this.userAccount = userAccount;
        this.userBan = userBan;
        this.userActivityCode = userActivityCode;
        this.userStatus = userStatus;
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

    public void setUserBan(boolean userBan) {
        this.userBan = userBan;
//        return this;
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
                ", userActivityCode='" + userActivityCode + '\'' +
                ", userStatus=" + userStatus +
                '}';
    }

    public String getUserSalt() {
        return userSalt;
    }

    public void setUserSalt(String userSalt) {
        this.userSalt = userSalt;
//        return this;
    }

    public String getUserActivityCode() {
        return userActivityCode;
    }

    public void setUserActivityCode(String userActivityCode) {
        this.userActivityCode = userActivityCode;
//        return this;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }

    public boolean getUserStatus() {
        return this.userStatus;
    }



}
