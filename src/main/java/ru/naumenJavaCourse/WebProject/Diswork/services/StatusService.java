package ru.naumenJavaCourse.WebProject.Diswork.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.naumenJavaCourse.WebProject.Diswork.models.Media;
import ru.naumenJavaCourse.WebProject.Diswork.models.Status;
import ru.naumenJavaCourse.WebProject.Diswork.models.Type;
import ru.naumenJavaCourse.WebProject.Diswork.repositories.StatusRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {

    private final StatusRepository statusRepository;

    @Autowired
    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Transactional
    public void save(Status type){
        statusRepository.save(type);
    }
    @Transactional(readOnly = true)
    public Optional<Status> findByName(String name){
        return statusRepository.findByStatusName(name);
    }

    @Transactional(readOnly = true)
    public List<Status> getAll(){
        return statusRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Status findById(int id){
        Optional<Status> optional = statusRepository.findById(id);
        if (optional.isEmpty())
            throw new EntityNotFoundException();

        return optional.get();
    }
}
