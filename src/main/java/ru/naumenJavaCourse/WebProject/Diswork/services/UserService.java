package ru.naumenJavaCourse.WebProject.Diswork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.naumenJavaCourse.WebProject.Diswork.models.Media;
import ru.naumenJavaCourse.WebProject.Diswork.models.MediaRatingKey;
import ru.naumenJavaCourse.WebProject.Diswork.models.User;
import ru.naumenJavaCourse.WebProject.Diswork.models.UserMedia;
import ru.naumenJavaCourse.WebProject.Diswork.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        if (user.getUserMedia().stream().map(UserMedia::getMedia).anyMatch(media1 -> media1.equals(media)))
            return "Данное произведение уже добавленно";
        else {
            var userMedia = new UserMedia();
            userMedia.setMedia(media);
            userMedia.setUser(user);
            user.getUserMedia().add(userMedia);
            userRepository.save(user);
            return "Произведение добавленно";
        }
    }

    @Transactional(readOnly = true)
    public Set<Media> showUserMedia(int userId){
        User user = this.findById(userId);
        return user.getUserMedia().stream().filter(userMedia -> userMedia.getUser().equals(user)).map(UserMedia::getMedia).collect(Collectors.toSet());
    }

    @Transactional
    public String deleteMediaFromList(int userId, int mediaId){
        User user = this.findById(userId);
        Media media = mediaService.findById(mediaId);
        if (user.getUserMedia().stream().map(UserMedia::getMedia).anyMatch(media1 -> media1.equals(media))){
            user.getUserMedia().removeIf(userMedia -> userMedia.getUser().equals(user) && userMedia.getMedia().equals(media));
            userRepository.save(user);
            return "Произведение удалено из избранного";
        }
        else {
            return "Произведения нету в избранном";
        }
    }
}
