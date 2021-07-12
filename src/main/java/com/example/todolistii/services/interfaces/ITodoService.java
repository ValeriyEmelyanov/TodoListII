package com.example.todolistii.services.interfaces;

import com.example.todolistii.domain.Todo;
import com.example.todolistii.dto.TodoDto;

import java.util.List;

public interface ITodoService {
    List<TodoDto> getAll(Long userId);
    TodoDto create(Todo todo, Long userId);
    TodoDto get(Long id);
    TodoDto update(Todo todo, Long id);
    TodoDto delete(Long id);
}
