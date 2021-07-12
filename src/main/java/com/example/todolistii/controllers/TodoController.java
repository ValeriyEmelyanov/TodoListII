package com.example.todolistii.controllers;

import com.example.todolistii.domain.Todo;
import com.example.todolistii.dto.TodoDto;
import com.example.todolistii.services.interfaces.ITodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

    private final ITodoService todoService;

    @Autowired
    public TodoController(ITodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/users/{user_id}/todos")
    public ResponseEntity<TodoDto> createTodo(@RequestBody Todo todo,
                                              @PathVariable(name = "user_id") Long userId) {
        TodoDto result = todoService.create(todo, userId);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
