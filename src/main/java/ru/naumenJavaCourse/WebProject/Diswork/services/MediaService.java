package ru.naumenJavaCourse.WebProject.Diswork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import ru.naumenJavaCourse.WebProject.Diswork.models.Media;
import ru.naumenJavaCourse.WebProject.Diswork.repositories.MediaRepository;

import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class MediaService {
    private final MediaRepository mediaRepository;

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

    private void saveImage(Media media, MultipartFile imageFile) {
        String absolutePathFolder = "src/main/webapp/resources/images/";
        String imageFolder = "/resources/images/";
        String format = ".png";
        try {
            var path = Paths.get(absolutePathFolder + media.getMediaName() + format);
            Files.write(path, imageFile.getBytes());
            media.setPosterPath(imageFolder + media.getMediaName() + format);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
    public Optional<Media> findByName(String name){
        return mediaRepository.findByMediaName(name);
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

    @Transactional
    public Media findById(int id){
        return mediaRepository.findById(id).get();
    }
}
