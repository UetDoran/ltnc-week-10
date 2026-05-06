package com.example.bai10;


public class User {
    private int id;
    private String fullName;
    private boolean agreedStatus;

    public User() {}

    public User(String fullName, boolean agreedStatus) {
        this.fullName = fullName;
        this.agreedStatus = agreedStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isAgreedStatus() {
        return agreedStatus;
    }

    public void setAgreedStatus(boolean agreedStatus) {
        this.agreedStatus = agreedStatus;
    }

    @Override
    public String toString() {
        return "User{" + "fullName='" + fullName + '\'' + ", agreedStatus=" + agreedStatus + '}';
    }
}