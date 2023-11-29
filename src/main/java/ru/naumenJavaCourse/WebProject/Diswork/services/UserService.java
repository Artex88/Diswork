package ru.naumenJavaCourse.WebProject.Diswork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.naumenJavaCourse.WebProject.Diswork.models.Media;
import ru.naumenJavaCourse.WebProject.Diswork.models.User;
import ru.naumenJavaCourse.WebProject.Diswork.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final MediaService mediaService;
    @Autowired
    public UserService(UserRepository userRepository, MediaService mediaService) {
        this.userRepository = userRepository;
        this.mediaService = mediaService;
    }
    @Transactional(readOnly = true)
    public User findById(int id){
        Optional<User> user = userRepository.findUserById(id);
        return user.orElse(null);
    }
    @Transactional(readOnly = true)
    public User findByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }
    @Transactional
    public String addMediaToList(int userId, int mediaId){
        User user = this.findById(userId);
        Media media = mediaService.findById(mediaId);
        if (user.getMediaList().contains(media))
            return "Данное произведение уже добавленно";
        else {
            user.getMediaList().add(media);
            userRepository.save(user);
            return "Произведение добавленно";
        }
    }

    @Transactional
    public String deleteMediaFromList(int userId, int mediaId){
        User user = this.findById(userId);
        Media media = mediaService.findById(mediaId);
        if (user.getMediaList().contains(media)){
            user.getMediaList().remove(media);
            userRepository.save(user);
            return "Произведение удалено из избранного";
        }
        else {
            return "Произведения нету в избранном";
        }
    }
}
