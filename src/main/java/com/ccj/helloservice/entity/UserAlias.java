package com.ccj.helloservice.entity;

public class UserAlias {
    private int id;
    private String userId;
    private String namealias;

    public String getNamealias() {
        return namealias;
    }

    public void setNamealias(String namealias) {
        this.namealias = namealias;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
