package com.nhnacademy.mart.support.repository;

import com.nhnacademy.mart.support.domain.User;

public interface UserRepository {
    User getUser(String id);
    void register(String id, String pwd, String name, String email, boolean admin);
    boolean exists(String id);
}
