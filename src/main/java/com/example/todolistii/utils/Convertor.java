package com.example.todolistii.utils;

import com.example.todolistii.domain.Tag;
import com.example.todolistii.domain.Todo;
import com.example.todolistii.domain.User;
import com.example.todolistii.dto.TagDto;
import com.example.todolistii.dto.TodoDto;
import com.example.todolistii.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class Convertor {

    public UserDto userToDto(User source) {
        UserDto result = new UserDto();
        result.setId(source.getId());
        result.setEmail(source.getEmail());
        result.setPassword(source.getPassword());
        result.setTodos(source.getTodos().stream()
                .map(this::todoToDto)
                .collect(Collectors.toSet()));
        return result;
    }

    public TodoDto todoToDto(Todo source) {
        TodoDto result = new TodoDto();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setComment(source.getComment());
        result.setStartDate(source.getStartDate());
        result.setEndDate(source.getEndDate());
        result.setImportant(source.getImportant());
        result.setPriority(source.getPriority());
        result.setUserId(source.getUser().getId());
        result.setTags(source.getTags().stream()
                .map(this::tagToDto)
                .collect(Collectors.toSet()));
        return result;
    }

    public TagDto tagToDto(Tag source) {
        TagDto result = new TagDto();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setTodoIds(source.getTodos().stream()
                .map(Todo::getId)
                .collect(Collectors.toSet()));
        return result;
    }
}
