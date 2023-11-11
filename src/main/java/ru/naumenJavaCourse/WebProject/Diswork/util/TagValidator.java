package ru.naumenJavaCourse.WebProject.Diswork.util;

import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.naumenJavaCourse.WebProject.Diswork.models.Tag;
import ru.naumenJavaCourse.WebProject.Diswork.models.User;
import ru.naumenJavaCourse.WebProject.Diswork.services.TagService;
@Component
public class TagValidator implements Validator {

    private final TagService tagService;

    @Autowired
    public TagValidator(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Tag.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Tag tag = (Tag) target;
        if (tagService.findByName(tag.getTagName()).isPresent())
            errors.rejectValue("tagName","","Тэг с таким названием уже существует");
    }
}
