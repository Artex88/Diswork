package ru.naumenJavaCourse.WebProject.Diswork.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.naumenJavaCourse.WebProject.Diswork.models.Media;
import ru.naumenJavaCourse.WebProject.Diswork.models.Status;
import ru.naumenJavaCourse.WebProject.Diswork.models.Tag;
import ru.naumenJavaCourse.WebProject.Diswork.models.Type;
import ru.naumenJavaCourse.WebProject.Diswork.repositories.MediaRepository;
import ru.naumenJavaCourse.WebProject.Diswork.repositories.TypeRepository;


import java.util.List;
import java.util.Optional;

@Service
public class TypeService {
    private final TypeRepository typeRepository;

    private final MediaService mediaService;

    @Autowired
    public TypeService(TypeRepository typeRepository, MediaService mediaService) {
        this.typeRepository = typeRepository;
        this.mediaService = mediaService;
    }

    @Transactional
    public void save(Type type){
        typeRepository.save(type);
    }

    @Transactional
    public void upload(Type newType, int oldTypeId){
        newType.setId(oldTypeId);
        typeRepository.save(newType);
    }
    @Transactional(readOnly = true)
    public Optional<Type> findByName(String name){
        return typeRepository.findByTypeName(name);
    }

    @Transactional(readOnly = true)
    public List<Type> getAll(){
        return typeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Type findById(int id){
        Optional<Type> optional = typeRepository.findById(id);
        if (optional.isEmpty())
            throw new EntityNotFoundException();

        return optional.get();
    }

    @Transactional()
    public void delete(int id){
        Type typeToDelete = this.findById(id);
        List<Media> mediaList = mediaService.findByType(typeToDelete);
        for (Media media : mediaList){
            media.setType(null);
        }
        typeRepository.delete(typeToDelete);
    }
}
