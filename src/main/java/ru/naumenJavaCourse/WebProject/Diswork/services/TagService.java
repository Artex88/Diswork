package ru.naumenJavaCourse.WebProject.Diswork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.naumenJavaCourse.WebProject.Diswork.models.Tag;
import ru.naumenJavaCourse.WebProject.Diswork.repositories.TagRepository;

import java.util.Optional;

@Service
public class TagService {

    private final TagRepository tagRepository;
    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Transactional
    public void save(Tag tag){
        tagRepository.save(tag);
    }

    public Optional<Tag> findByName(String name){
        return tagRepository.findByTagName(name);
    }
}
