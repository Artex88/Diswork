package ru.naumenJavaCourse.WebProject.Diswork.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.naumenJavaCourse.WebProject.Diswork.models.Media;
import ru.naumenJavaCourse.WebProject.Diswork.services.MediaService;
@Component
public class MediaValidator implements Validator {

    private final MediaService mediaService;

    @Autowired
    public MediaValidator(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Media.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Media media = (Media) target;
        if (mediaService.findByName(media.getMediaName()).isPresent()){
            errors.rejectValue("mediaName","","Произведение с таким названием уже существует");
        }
    }
}
