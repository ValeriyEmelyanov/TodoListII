package com.example.todolistii.controllers;

import com.example.todolistii.domain.Todo;
import com.example.todolistii.dto.TodoDto;
import com.example.todolistii.services.interfaces.ITodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TodoController {

    private final ITodoService todoService;

    @Autowired
    public TodoController(ITodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/users/{userId}/todos")
    public ResponseEntity<TodoDto> createTodo(@RequestBody Todo todo,
                                              @PathVariable(name = "userId") Long userId) {
        TodoDto result = todoService.create(todo, userId);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}/todos")
    public ResponseEntity<List<TodoDto>> getAll(@PathVariable(name = "userId") Long userId) {
        List<TodoDto> result = todoService.getAll(userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/todos/{id}")
    public ResponseEntity<TodoDto> getTodo(@PathVariable(name = "userId") Long userId,
                                           @PathVariable(name = "id") Long id) {
        TodoDto result = todoService.get(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/users/{userId}/todos/{id}")
    public ResponseEntity<TodoDto> updateTodo(@RequestBody Todo todo,
                                              @PathVariable(name = "userId") Long userId,
                                              @PathVariable(name = "id") Long id) {
        TodoDto result = todoService.update(todo, id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}/todos/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable(name = "userId") Long userId,
                                           @PathVariable(name = "id") Long id) {
        String result = todoService.delete(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
