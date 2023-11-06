package ru.naumenJavaCourse.WebProject.Diswork.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.naumenJavaCourse.WebProject.Diswork.models.User;
import ru.naumenJavaCourse.WebProject.Diswork.services.UserValidateService;

@Component
public class UserValidator implements Validator {

    private final UserValidateService userValidateService ;
    @Autowired
    public UserValidator(UserValidateService userValidateService) {
        this.userValidateService = userValidateService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userValidateService.findUserByLogin(user.getLogin()).isEmpty())
            errors.rejectValue("username","","Человек с таким пользователем существует");
    }
}
