package com.example.todolistii.controllers;

import com.example.todolistii.domain.User;
import com.example.todolistii.dto.UserDto;
import com.example.todolistii.security.TokenManager;
import com.example.todolistii.security.TokenPayload;
import com.example.todolistii.services.interfaces.IUserService;
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

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class UserController {

    private final IUserService userService;
    private final TokenManager tokenManager;

    @Autowired
    public UserController(IUserService userService, TokenManager tokenManager) {
        this.userService = userService;
        this.tokenManager = tokenManager;
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody User user) {
        UserDto result = userService.create(user);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/auth")
    public ResponseEntity<String> authenticateUser(@RequestBody User user) {
        UserDto authenticatedUser = userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
        if (authenticatedUser == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        String token = tokenManager.createToken(new TokenPayload(authenticatedUser.getId(),
                authenticatedUser.getEmail(),
                Calendar.getInstance().getTime()));

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> result = userService.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable long id) {
        UserDto result = userService.get(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody User user, @PathVariable long id) {
        UserDto result = userService.update(id, user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        String result = userService.delete(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Exception handling
     */

    @ExceptionHandler
    public ResponseEntity<String> onConflictUserEmail(DataIntegrityViolationException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ClassUtils.getShortName(exception.getClass())
                        + ": May be user with such email already registered");
    }

    @ExceptionHandler
    public ResponseEntity<String> onMissingUserId(NoSuchElementException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ClassUtils.getShortName(exception.getClass())
                        + ": No such user was found");
    }

    @ExceptionHandler
    public ResponseEntity<String> onMissingUser(EmptyResultDataAccessException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ClassUtils.getShortName(exception.getClass())
                        + ": No one user was found");
    }

    @ExceptionHandler
    public ResponseEntity<String> onSqlProblem(SQLException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ClassUtils.getShortName(exception.getClass())
                        + exception.getSQLState()
                        + exception.getLocalizedMessage()
                        + ": SQL error with user");
    }
}
