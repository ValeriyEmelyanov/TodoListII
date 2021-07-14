package com.example.todolistii.services.interfaces;

import com.example.todolistii.domain.User;
import com.example.todolistii.dto.UserDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IUserService {

    List<UserDto> getAll();
    UserDto create(User user);
    UserDto get(long id);
    UserDto findUserByEmailAndPassword(String email, String password);
    UserDto update(long id, User user);
    String delete(long id);
}
