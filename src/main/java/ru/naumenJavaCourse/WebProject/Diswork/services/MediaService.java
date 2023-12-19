package ru.naumenJavaCourse.WebProject.Diswork.services;

import jakarta.persistence.EntityNotFoundException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import ru.naumenJavaCourse.WebProject.Diswork.models.Media;
import ru.naumenJavaCourse.WebProject.Diswork.models.Status;
import ru.naumenJavaCourse.WebProject.Diswork.models.Tag;
import ru.naumenJavaCourse.WebProject.Diswork.models.Type;
import ru.naumenJavaCourse.WebProject.Diswork.repositories.MediaRepository;

import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


@Service
public class MediaService {
    private final MediaRepository mediaRepository;

    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList("image/png", "image/webp");

    private static final long MAX_IMAGE_SIZE = 204800;

    @Autowired
    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }
    @Transactional
    public void save(Media media, MultipartFile imageFile){
        saveImage(media, imageFile);
        media.setRating(0);
        mediaRepository.save(media);
    }

    @Transactional
    public List<Media> getAll(){
        return mediaRepository.findAll();
    }
    @Transactional()
    public void delete(int id){
        mediaRepository.removeById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Media> findByName(String name){
        return mediaRepository.findByMediaName(name);
    }

    @Transactional(readOnly = true)
    public Media findById(int id){
        Optional<Media> optional = mediaRepository.findById(id);
        if (optional.isEmpty())
            throw new EntityNotFoundException();
        return optional.get();
    }

    @Transactional
    public void upload(Media newMedia, int oldMediaId, MultipartFile imageFile){
        newMedia.setId(oldMediaId);
        if (imageFile.isEmpty())
            newMedia.setPosterPath(this.findById(oldMediaId).getPosterPath());
        else
            saveImage(newMedia, imageFile);
        mediaRepository.save(newMedia);
    }

    private void saveImage(Media media, MultipartFile imageFile) {
        String contentType = validateImageAndGetContentType(imageFile);
        String absolutePathFolder = "src/main/webapp/resources/images/";
        String imageFolder = "/resources/images/";
            try {
                String posterPath = media.getPosterPath();
                if (posterPath != null){
                    Files.delete(Paths.get(absolutePathFolder + media.getMediaName() + "." + contentType));
                }
                var path = Paths.get(absolutePathFolder + media.getMediaName() +  "." + contentType);
                Files.write(path, imageFile.getBytes());
                media.setPosterPath(imageFolder + media.getMediaName() + "." + contentType);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    private String validateImageAndGetContentType(MultipartFile imageFile){
        if (imageFile.isEmpty() || imageFile.getSize() == 0)
            throw new RuntimeException();
        String contentType = imageFile.getContentType();
        if (contentType == null || !ALLOWED_IMAGE_TYPES.contains(contentType))
            throw new RuntimeException();
        if (imageFile.getSize() > MAX_IMAGE_SIZE)
            throw new RuntimeException();
        return contentType.substring(6);
    }

    @Transactional(readOnly = true)
    public void updateRating(int mediaId){
        BigDecimal sumOfAllGrades = mediaRepository.getTotalNumberOfRatingPointsRating(mediaId);
        BigDecimal sumOfTimesWhenMediaGrated = mediaRepository.getNumberOfTimesWhenMediaGraded(mediaId);
        BigDecimal avgRating = null;
        try {
            avgRating = sumOfAllGrades.divide(sumOfTimesWhenMediaGrated, 2, RoundingMode.HALF_UP);
        } catch (Exception e){
            mediaRepository.updateMediaByRating(new BigDecimal(0), mediaId);
        }

        mediaRepository.updateMediaByRating(avgRating, mediaId);
    }

    @Transactional(readOnly = true)
    public List<Media> getAllMediaAndSort(String orderSetting){
        List<Media> mediaList =  mediaRepository.findAll();
        if (Objects.equals(orderSetting, "id"))
            mediaList.sort(Comparator.comparing(Media::getId));
        else if(Objects.equals(orderSetting, "mediaName"))
            mediaList.sort(Comparator.comparing(Media::getYearOfRelease));
        else if(Objects.equals(orderSetting, "yearOfRelease"))
            mediaList.sort(Comparator.comparing(Media::getMediaName));
        else if(Objects.equals(orderSetting, "rating"))
            mediaList.sort(Comparator.comparing(Media::getRating));
        return mediaList;
    }

    public List<Media> filterMedia(Type type, Status status, String episodeDuration, String releasePeriod, Set<Tag> tagSet ){
        return mediaRepository.filter(type, status, releasePeriod, episodeDuration, tagSet, tagSet.size());
    }

}
