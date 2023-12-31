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
import ru.naumenJavaCourse.WebProject.Diswork.repositories.StatusRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StatusService {

    private final StatusRepository statusRepository;

    private final MediaService mediaService;

    @Autowired
    public StatusService(StatusRepository statusRepository, MediaService mediaService) {
        this.statusRepository = statusRepository;
        this.mediaService = mediaService;
    }

    @Transactional
    public void save(Status type){
        statusRepository.save(type);
    }

    @Transactional
    public void upload(Status newStatus, int oldStatusId){
        newStatus.setId(oldStatusId);
        statusRepository.save(newStatus);
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

    @Transactional()
    public void delete(int id){
        Status statusToDelete = this.findById(id);
        List<Media> mediaList = mediaService.findByStatus(statusToDelete);
        for (Media media : mediaList){
            media.setStatus(null);
        }
        statusRepository.delete(statusToDelete);
    }
}
