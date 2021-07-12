package com.example.todolistii.services;

import com.example.todolistii.domain.Tag;
import com.example.todolistii.repositories.TagRepository;
import com.example.todolistii.services.interfaces.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagService implements ITagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    @Transactional
    public Tag getOrCreate(Tag tag) {
        Optional<Tag> found = tagRepository.findByName(tag.getName());
        if (found.isEmpty()) {
            tagRepository.save(tag);
            return tag;
        }
        return found.get();
    }
}
