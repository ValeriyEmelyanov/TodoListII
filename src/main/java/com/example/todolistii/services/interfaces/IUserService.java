package com.example.todolistii.services.interfaces;

import com.example.todolistii.domain.User;
import com.example.todolistii.dto.UserDto;

import java.util.List;

public interface IUserService {

    List<UserDto> getAll();
    UserDto create(User user);
    UserDto get(long id);
    UserDto update(long id, User user);
    UserDto delete(long id);
}
