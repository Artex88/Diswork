package ru.naumenJavaCourse.WebProject.Diswork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.naumenJavaCourse.WebProject.Diswork.models.Type;
import ru.naumenJavaCourse.WebProject.Diswork.repositories.TypeRepository;


import java.util.List;
import java.util.Optional;

@Service
public class TypeService {
    private final TypeRepository typeRepository;

    @Autowired
    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Transactional
    public void save(Type type){
        typeRepository.save(type);
    }
    @Transactional(readOnly = true)
    public Optional<Type> findByName(String name){
        return typeRepository.findByTypeName(name);
    }

    @Transactional(readOnly = true)
    public List<Type> getAll(){

        return typeRepository.findAll();
    }
}
