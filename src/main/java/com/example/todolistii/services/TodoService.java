package com.example.todolistii.services;

import com.example.todolistii.domain.Tag;
import com.example.todolistii.domain.Todo;
import com.example.todolistii.domain.User;
import com.example.todolistii.dto.TodoDto;
import com.example.todolistii.exceptions.EmptyDataException;
import com.example.todolistii.repositories.TodoRepository;
import com.example.todolistii.repositories.UserRepository;
import com.example.todolistii.services.interfaces.ITagService;
import com.example.todolistii.services.interfaces.ITodoService;
import com.example.todolistii.utils.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TodoService implements ITodoService {

    private final Convertor convertor;
    private final ITagService tagService;
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Autowired
    public TodoService(Convertor convertor, ITagService tagService, TodoRepository todoRepository, UserRepository userRepository) {
        this.convertor = convertor;
        this.tagService = tagService;
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoDto> getAll(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return new ArrayList<>();
        }
        return todoRepository.findAllByUser(optionalUser.get()).stream()
                .map(convertor::todoToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TodoDto create(Todo todo, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new EmptyDataException(String.format("Unable to get user with id: %d", userId));
        }

        Set<Tag> tags = new HashSet<>(todo.getTags().size());
        tags.addAll(todo.getTags());

        todo.getTags().clear();

        todo.setUser(optionalUser.get());
        todoRepository.save(todo);

        tags.stream()
                .map(tagService::getOrCreate)
                .collect(Collectors.toSet())
                .forEach(todo::addTag);

        return convertor.todoToDto(todo);
    }

    @Override
    @Transactional(readOnly = true)
    public TodoDto get(Long id) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.isEmpty()) {
            throw new EmptyDataException(String.format("Unable to get todo with id: %d", id));
        }

        return convertor.todoToDto(optionalTodo.get());
    }

    @Override
    @Transactional
    public TodoDto update(Todo todo, Long id) {
        Optional<Todo> foundOptional = todoRepository.findById(id);
        if (foundOptional.isEmpty()) {
            throw new EmptyDataException(String.format("Unable to update todo with id: %d", id));
        }
        Todo target = foundOptional.get();
        target.setName(todo.getName());
        target.setComment(todo.getComment());
        target.setStartDate(todo.getStartDate());
        target.setEndDate(todo.getEndDate());
        target.setImportant(todo.getImportant());
        target.setPriority(todo.getPriority());

        todoRepository.save(target);

        return convertor.todoToDto(target);
    }

    @Override
    @Transactional
    public String delete(Long id) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.isEmpty()) {
            throw new EmptyDataException(String.format("Unable to delete todo with id: %d", id));
        }
        Todo todo = optionalTodo.get();
        new HashSet<>(todo.getTags())
                .forEach(tag -> tag.removeTodo(todo));
        todoRepository.deleteById(id);
        return String.format("Todo with id: %d was successfully removed", id);
    }
}
