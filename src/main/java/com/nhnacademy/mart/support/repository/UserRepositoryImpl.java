package com.nhnacademy.mart.support.repository;

import com.nhnacademy.mart.support.domain.CSManager;
import com.nhnacademy.mart.support.domain.Customer;
import com.nhnacademy.mart.support.domain.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository{
    private final Map<String , User> users = new HashMap<>();

    @Override
    public User getUser(String id) {
        return users.get(id);
    }

    @Override
    public void register(String id, String pwd,String name, String email, boolean admin) {
        User user;

        if(admin){
            user = new CSManager(id, pwd, name, email);
        }else{
            user = new Customer(id, pwd, name, email);
        }
        users.put(id,user);
    }

    @Override
    public boolean exists(String id) {
        return users.containsKey(id);
    }
}
