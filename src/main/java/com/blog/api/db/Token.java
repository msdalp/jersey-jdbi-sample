package com.blog.api.db;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;

public class Token {

    @JsonIgnore
    private int id;
    private String token;
    private int userId;
    private Timestamp expireAt;

    public Token() {
    }

    public Token(int id, String token, int userId, Timestamp expireAt) {
        this.id = id;
        this.token = token;
        this.userId = userId;
        this.expireAt = expireAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Timestamp expireAt) {
        this.expireAt = expireAt;
    }
}
