package com.example.todolistii.services.interfaces;

import com.example.todolistii.domain.User;

public interface IUserService {

    int create(User user);
    User get(long id);
    int update(long id, User user);
    int delete(long id);
    void createTable();
}
