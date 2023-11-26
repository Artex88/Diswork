package ru.naumenJavaCourse.WebProject.Diswork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.naumenJavaCourse.WebProject.Diswork.models.Media;
import ru.naumenJavaCourse.WebProject.Diswork.repositories.MediaRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
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
        String absolutePathFolder = "src/main/webapp/resources/static/images/";
        String imageFolder = "/images/";
        String format = ".png";
        try {
            // TODO Вынести это в отдельный метод
            var path = Paths.get(absolutePathFolder + media.getMediaName() + format);
            Files.write(path,imageFile.getBytes());
            media.setPosterPath(imageFolder + media.getMediaName() + format);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        mediaRepository.save(media);
    }

    @Transactional(readOnly = true)
    public Optional<Media> findByName(String name){
        return mediaRepository.findByMediaName(name);
    }

    @Transactional(readOnly = true)
    public List<Media> getAll(){
        return mediaRepository.findAll();
    }

    @Transactional
    public Media findById(int id){
        return mediaRepository.findById(id).get();
    }

}
