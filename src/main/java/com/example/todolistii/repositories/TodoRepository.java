package com.example.todolistii.repositories;

import com.example.todolistii.domain.Todo;
import com.example.todolistii.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {
    List<Todo> findAllByUser(User user);
}
