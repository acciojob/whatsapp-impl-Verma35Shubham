package com.driver;

import java.util.HashSet;

public class User {
    private String name;
    private String mobile;

    static HashSet<String> userdb;

    public User(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
        userdb =  new HashSet<>();
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
