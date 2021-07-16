package com.example.todolistii.utils;

import com.example.todolistii.domain.User;

public class Users {

    public static User newUser(String email, String password) {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);

        return newUser;
    }
}
