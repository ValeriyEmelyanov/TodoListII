package com.example.todolistii.services;

import com.example.todolistii.domain.Tag;
import com.example.todolistii.services.interfaces.ITagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class TagService implements ITagService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Tag getOrCreate(Tag tag) {
        List<Tag> found = entityManager.createQuery("SELECT tag FROM Tag tag WHERE tag.name=:name", Tag.class)
                .setParameter("name", tag.getName())
                .getResultList();
        if (found.isEmpty()) {
            entityManager.persist(tag);
            return tag;
        }
        return found.get(0);
    }
}
