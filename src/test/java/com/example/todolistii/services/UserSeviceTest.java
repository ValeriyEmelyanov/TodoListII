package com.example.todolistii.services;


import com.example.todolistii.domain.User;
import com.example.todolistii.dto.UserDto;
import com.example.todolistii.services.interfaces.IUserService;
import com.example.todolistii.utils.Users;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.Assert.*;

public class UserSeviceTest {

    private final String EMAIL = "user@gmail.ru";
    private final String PASSWORD = "123";

    private ApplicationContext applicationContext;
    private IUserService userService;

    @Before
    public void init() {
        this.applicationContext = new ClassPathXmlApplicationContext("/mainTest.xml");
        this.userService = applicationContext.getBean(IUserService.class);
    }

    @After
    public void cleanDB() {
        userService.getAll().forEach(userDto -> userService.delete(userDto.getId()));
    }

    @Test
    public void create() {
        User newUser = Users.newUser(EMAIL, PASSWORD);

        UserDto actual = userService.create(newUser);

        assertEquals(EMAIL, actual.getEmail());
        assertEquals(PASSWORD, actual.getPassword());
        assertNotNull(actual.getId());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void createWithNotUniqueEmail() {
        User newUser = Users.newUser(EMAIL, PASSWORD);
        userService.create(newUser);

        User newUser2 = Users.newUser(EMAIL, PASSWORD);
        userService.create(newUser2);
    }

    @Test
    public void get() {
        User newUser = Users.newUser(EMAIL, PASSWORD);

        UserDto saved = userService.create(newUser);

        UserDto actual = userService.get(saved.getId());

        assertEquals(saved.getId(), actual.getId());
        assertEquals(EMAIL, actual.getEmail());
        assertEquals(PASSWORD, actual.getPassword());
    }

    @Test
    public void findUserByEmailAndPassword() {
        User newUser = Users.newUser(EMAIL, PASSWORD);

        UserDto saved = userService.create(newUser);

        UserDto actual = userService.findUserByEmailAndPassword(EMAIL, PASSWORD);

        assertEquals(saved.getId(), actual.getId());
        assertEquals(EMAIL, actual.getEmail());
        assertEquals(PASSWORD, actual.getPassword());
    }

    @Test
    public void update() {
        User newUser = Users.newUser(EMAIL, PASSWORD);

        UserDto saved = userService.create(newUser);

        User forUpdate = new User();
        forUpdate.setEmail("new@gmail.com");
        forUpdate.setPassword("321");

        UserDto actual = userService.update(saved.getId(), forUpdate);

        assertEquals(saved.getId(), actual.getId());
        assertEquals(forUpdate.getEmail(), actual.getEmail());
        assertEquals(forUpdate.getPassword(), actual.getPassword());
    }

    @Test
    public void delete() {
        User newUser = Users.newUser(EMAIL, PASSWORD);

        UserDto saved = userService.create(newUser);

        List<UserDto> userDtos = userService.getAll();
        assertEquals(1, userDtos.size());

        userService.delete(saved.getId());

        userDtos = userService.getAll();
        assertEquals(0, userDtos.size());
    }
 }