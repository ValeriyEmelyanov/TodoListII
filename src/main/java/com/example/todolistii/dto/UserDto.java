package com.example.todolistii.dto;

import java.util.HashSet;
import java.util.Set;

public class UserDto {

    private Long id;
    private String email;
    private String password;
    private Set<TodoDto> todos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<TodoDto> getTodos() {
        return todos;
    }

    public void setTodos(Set<TodoDto> todos) {
        this.todos = todos;
    }
}
