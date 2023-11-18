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
        String folder = "src/main/resources/posters/";
        try {
            // TODO Вынести это в отдельный метод
            byte[]  bytes = imageFile.getBytes();
            var path = Paths.get(folder + media.getMediaName() + ".png");
            media.setPosterLink(path.toString());
            Files.write(path,bytes);
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

}
