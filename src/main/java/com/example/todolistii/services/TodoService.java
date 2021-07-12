package com.example.todolistii.services;

import com.example.todolistii.domain.Tag;
import com.example.todolistii.domain.Todo;
import com.example.todolistii.domain.User;
import com.example.todolistii.dto.TodoDto;
import com.example.todolistii.services.interfaces.ITagService;
import com.example.todolistii.services.interfaces.ITodoService;
import com.example.todolistii.utils.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TodoService implements ITodoService {

    @PersistenceContext
    private EntityManager entityManager;

    private final Convertor convertor;

    private final ITagService tagService;

    @Autowired
    public TodoService(Convertor convertor, ITagService tagService) {
        this.convertor = convertor;
        this.tagService = tagService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoDto> getAll(Long userId) {
        return null;
    }

    @Override
    @Transactional
    public TodoDto create(Todo todo, Long userId) {
        User user = entityManager.createQuery("SELECT user FROM User user WHERE user.id=:id", User.class)
                .setParameter("id", userId)
                .getSingleResult();
        todo.setUser(user);

        Set<Tag> tags = new HashSet<>(todo.getTags().size());
        tags.addAll(todo.getTags());

        todo.getTags().clear();

        entityManager.persist(todo);

        tags.stream()
                .map(tagService::getOrCreate)
                .collect(Collectors.toSet())
                .forEach(todo::addTag);

        return convertor.todoToDto(todo);
    }

    @Override
    @Transactional(readOnly = true)
    public TodoDto get(Long id) {
        return null;
    }

    @Override
    @Transactional
    public TodoDto update(Todo todo, Long id) {
        return null;
    }

    @Override
    @Transactional
    public TodoDto delete(Long id) {
        return null;
    }
}
