package com.example.springmvc.entity;

import java.util.Date;

public class LoginTicket {

    private int id;
    private int userId;
    private String ticket;
    private int status;
    private Date expired;

    public int getId() {
        return id;
    }

    public LoginTicket setId(int id) {
        this.id = id;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public LoginTicket setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getTicket() {
        return ticket;
    }

    public LoginTicket setTicket(String ticket) {
        this.ticket = ticket;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public LoginTicket setStatus(int status) {
        this.status = status;
        return this;
    }

    public Date getExpired() {
        return expired;
    }

    public LoginTicket setExpired(Date expired) {
        this.expired = expired;
        return this;
    }

    @Override
    public String toString() {
        return "LoginTicket{" +
                "id=" + id +
                ", userId=" + userId +
                ", ticket='" + ticket + '\'' +
                ", status=" + status +
                ", expired=" + expired +
                '}';
    }
}
