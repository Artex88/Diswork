package ru.naumenJavaCourse.WebProject.Diswork.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import ru.naumenJavaCourse.WebProject.Diswork.models.Media;
import ru.naumenJavaCourse.WebProject.Diswork.models.MediaRatingKey;
import ru.naumenJavaCourse.WebProject.Diswork.models.User;
import ru.naumenJavaCourse.WebProject.Diswork.models.UserMedia;
import ru.naumenJavaCourse.WebProject.Diswork.repositories.MediaRepository;
import ru.naumenJavaCourse.WebProject.Diswork.repositories.UserRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final MediaRepository mediaRepository;

    private final MediaService mediaService;
    @Autowired
    public UserService(UserRepository userRepository, MediaRepository mediaRepository, MediaService mediaService) {
        this.userRepository = userRepository;
        this.mediaRepository = mediaRepository;
        this.mediaService = mediaService;
    }
    @Transactional(readOnly = true)
    public User findById(int id){
        Optional<User> user = userRepository.findUserById(id);
        return user.orElse(null);
    }
    @Transactional(readOnly = true)
    public User findByAttributeSessionId(Object id){
        if (id == null)
            return null;
        else {
            return this.findById((int) id);
        }
    }
    @Transactional(readOnly = true)
    public User findByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }

    @Transactional(readOnly = true)
    public Set<Media> showUserMedia(int userId){
        User user = this.findById(userId);
        return user.getUserMedia().stream().filter(userMedia -> userMedia.getMediaRatingKey().getUserId() == userId).map(UserMedia::getMedia).collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public boolean isMediaExistInUserList(User user, int mediaId){
        return user.getUserMedia().stream().anyMatch(userMedia -> userMedia.getMediaRatingKey().getUserId() == user.getId() && userMedia.getMediaRatingKey().getMediaId() == mediaId);
    }
    @Transactional()
    public boolean canGradeMediaInUserList(User user, int userId, int mediaId, int grade){
        return user.getUserMedia().stream().anyMatch(userMedia -> {
            if (userMedia.getMediaRatingKey().getUserId() == userId && userMedia.getMediaRatingKey().getMediaId() == mediaId){
                userMedia.setGrade(grade);
                mediaService.updateRating(mediaId);
                return true;
            }
            return false;
        });
    }

    @Transactional()
    public boolean canDeleteMediaInUserList(User user, int userId, int mediaId){
        return user.getUserMedia().stream().anyMatch(userMedia -> {
            if (userMedia.getMediaRatingKey().getUserId() == userId && userMedia.getMediaRatingKey().getMediaId() == mediaId) {
                Media media = mediaService.findById(mediaId);
                user.getUserMedia().removeIf(userMedia1 -> userMedia1.getUser().equals(user) && userMedia1.getMedia().equals(media));
                mediaService.updateRating(mediaId);
                return true;
            }
            return false;
        });
    }

    @Transactional
        public UserMedia getCertainUserMedia(User user, int userId, int mediaId){
        return user.getUserMedia().stream().filter(userMedia -> userMedia.getMediaRatingKey().getMediaId() == mediaId && userMedia.getMediaRatingKey().getUserId() == userId).findFirst().get();
    }

    @Transactional
    public String addMediaToList(int userId, int mediaId){
        User user = this.findById(userId);
        if (this.isMediaExistInUserList(user,mediaId))
            return "Данное произведение уже добавленно";
        else{
            Media media = mediaService.findById(mediaId);
            var newUserMedia = new UserMedia();
            newUserMedia.setMedia(media);
            newUserMedia.setUser(user);
            user.getUserMedia().add(newUserMedia);
            userRepository.save(user);
            return "Произведение добавленно";
        }
    }

    @Transactional
    public String deleteMediaFromList(int userId, int mediaId){
        User user = this.findById(userId);
        if (this.canDeleteMediaInUserList(user,userId,mediaId))
            return "Произведение удалено из избранного";
        else
            return "Произведения нету в избранном";
    }
    @Transactional
    public String assessmentMedia(int userId, int mediaId, int grade){
        User user = this.findById(userId);
        if (this.canGradeMediaInUserList(user,userId,mediaId, grade))
            return String.valueOf(grade);
        else
            return "Произведения нету в избранном";
    }
}
