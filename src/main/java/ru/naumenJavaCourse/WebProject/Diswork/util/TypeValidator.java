package ru.naumenJavaCourse.WebProject.Diswork.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.naumenJavaCourse.WebProject.Diswork.models.Tag;
import ru.naumenJavaCourse.WebProject.Diswork.models.Type;
import ru.naumenJavaCourse.WebProject.Diswork.services.TypeService;

@Component
public class TypeValidator implements Validator {

    private final TypeService typeService;

    @Autowired
    public TypeValidator(TypeService typeService) {
        this.typeService = typeService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Type.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Type type = (Type) target;
        if (typeService.findByName(type.getTypeName()).isPresent())
            errors.rejectValue("typeName","","Тип произведения с таким названием уже существует");
    }
}
