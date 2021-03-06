package com.example.todolistii.controllers;

import com.example.todolistii.annotations.Authenticational;
import com.example.todolistii.domain.Todo;
import com.example.todolistii.dto.TodoDto;
import com.example.todolistii.services.interfaces.ITodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class TodoController {

    private final ITodoService todoService;

    private Long userId;

    @Autowired
    public TodoController(ITodoService todoService) {
        this.todoService = todoService;
    }

    @Authenticational
    @PostMapping("/users/{userId}/todos")
    public ResponseEntity<TodoDto> createTodo(HttpServletRequest request,
                                              @RequestBody Todo todo,
                                              @PathVariable(name = "userId") Long userId) {
        TodoDto result = todoService.create(todo, userId);
        // Можно взять userId, который пришел из token
        // TodoDto result = todoService.create(todo, this.userId);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Authenticational
    @GetMapping("/users/{userId}/todos")
    public ResponseEntity<List<TodoDto>> getAll(HttpServletRequest request,
                                                @PathVariable(name = "userId") Long userId) {
        List<TodoDto> result = todoService.getAll(userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Authenticational
    @GetMapping("/users/{userId}/todos/{id}")
    public ResponseEntity<TodoDto> getTodo(HttpServletRequest request,
                                           @PathVariable(name = "userId") Long userId,
                                           @PathVariable(name = "id") Long id) {
        TodoDto result = todoService.get(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Authenticational
    @PutMapping("/users/{userId}/todos/{id}")
    public ResponseEntity<TodoDto> updateTodo(HttpServletRequest request,
                                              @RequestBody Todo todo,
                                              @PathVariable(name = "userId") Long userId,
                                              @PathVariable(name = "id") Long id) {
        TodoDto result = todoService.update(todo, id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Authenticational
    @DeleteMapping("/users/{userId}/todos/{id}")
    public ResponseEntity<String> deleteTodo(HttpServletRequest request,
                                             @PathVariable(name = "userId") Long userId,
                                             @PathVariable(name = "id") Long id) {
        String result = todoService.delete(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Exception handling
     */

    @ExceptionHandler
    public ResponseEntity<String> onEmptyTodoName(DataIntegrityViolationException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ClassUtils.getShortName(exception.getClass())
                        + ": May be todo name is empty");
    }

    @ExceptionHandler
    public ResponseEntity<String> onMissingTodoId(NoSuchElementException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ClassUtils.getShortName(exception.getClass())
                        + ": No such todo was found");
    }

    @ExceptionHandler
    public ResponseEntity<String> onMissingTodo(EmptyResultDataAccessException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ClassUtils.getShortName(exception.getClass())
                        + ": No one todo was found");
    }

    @ExceptionHandler
    public ResponseEntity<String> onSqlProblem(SQLException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ClassUtils.getShortName(exception.getClass())
                        + exception.getSQLState()
                        + exception.getLocalizedMessage()
                        + ": SQL error with todo");
    }
}
