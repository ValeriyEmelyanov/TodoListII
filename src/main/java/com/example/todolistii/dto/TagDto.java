package com.example.todolistii.dto;

import java.util.HashSet;
import java.util.Set;

public class TagDto {

    private Long id;
    private String name;
    private Set<Long> todoIds = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Long> getTodoIds() {
        return todoIds;
    }

    public void setTodoIds(Set<Long> todoIds) {
        this.todoIds = todoIds;
    }
}
