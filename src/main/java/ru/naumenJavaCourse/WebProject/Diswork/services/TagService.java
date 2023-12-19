package ru.naumenJavaCourse.WebProject.Diswork.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.naumenJavaCourse.WebProject.Diswork.models.Status;
import ru.naumenJavaCourse.WebProject.Diswork.models.Tag;
import ru.naumenJavaCourse.WebProject.Diswork.repositories.TagRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Transactional
    public void upload(Tag newTag, int oldTagId){
        newTag.setId(oldTagId);
        tagRepository.save(newTag);
    }
    @Transactional(readOnly = true)
    public Optional<Tag> findByName(String name){
        return tagRepository.findByTagName(name);
    }

    @Transactional(readOnly = true)
    public List<Tag> getAll(){
        return tagRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Tag findById(int id){
        Optional<Tag> optional = tagRepository.findById(id);
        if (optional.isEmpty())
            throw new EntityNotFoundException();

        return optional.get();
    }

    @Transactional()
    public void delete(int id){
        tagRepository.removeById(id);
    }

    @Transactional
    public Set<Tag> getTagsListFromIds(Set<Integer> integerSet){
        Set<Tag> tagSet = new HashSet<>();
        for (var id : integerSet){
            tagSet.add(this.findById(id));
        }
        return tagSet;
    }
}
