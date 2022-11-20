package com.nhnacademy.mart.support.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private String id;
    private String pwd;
    private String name;
    private String email;

    public User(String id, String pwd,String name, String email) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.email = email;
    }
}
