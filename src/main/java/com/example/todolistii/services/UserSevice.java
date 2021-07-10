package com.example.todolistii.services;

import com.example.todolistii.domain.User;
import com.example.todolistii.services.interfaces.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserSevice implements IUserService {

    @Override
    public int create(User user) {
        return 0;
    }

    @Override
    public User get(long id) {
        return null;
    }

    @Override
    public int update(long id, User user) {
        return 0;
    }

    @Override
    public int delete(long id) {
        return 0;
    }
}
