package com.example.todolistii.controllers;

import com.example.todolistii.domain.User;
import com.example.todolistii.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/create_table")
    public ResponseEntity<String> getCreateTable() {
        userService.createTable();
        return new ResponseEntity<>("Table has been created", HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<Integer> createUser(@RequestBody User user) {
        int result = userService.create(user);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        User result = userService.get(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Integer> updateUser(@RequestBody User user, @PathVariable long id) {
        int result = userService.update(id, user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Integer> deleteUser(@PathVariable long id) {
        int result = userService.delete(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
