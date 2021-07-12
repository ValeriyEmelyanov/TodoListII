package com.example.todolistii.repositories;

import com.example.todolistii.domain.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
}
