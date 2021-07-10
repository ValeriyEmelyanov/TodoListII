package com.example.todolistii.controllers;

import com.example.todolistii.domain.Todo;
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

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/todos")
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        return new ResponseEntity<>(todo, HttpStatus.CREATED);
    }

//    @GetMapping("/users/{id}")
//    public ResponseEntity<User> getUser(@PathVariable long id) {
//        User result = userService.get(id);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @PutMapping("/users/{id}")
//    public ResponseEntity<Integer> updateUser(@RequestBody User user, @PathVariable long id) {
//        int result = userService.update(id, user);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/users/{id}")
//    public ResponseEntity<Integer> deleteUser(@PathVariable long id) {
//        int result = userService.delete(id);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

}
