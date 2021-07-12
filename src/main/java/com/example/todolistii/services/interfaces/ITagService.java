package com.example.todolistii.services.interfaces;

import com.example.todolistii.domain.Tag;

public interface ITagService {
    Tag getOrCreate(Tag tag);
}
