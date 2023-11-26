package ru.naumenJavaCourse.WebProject.Diswork.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.naumenJavaCourse.WebProject.Diswork.models.Status;
import ru.naumenJavaCourse.WebProject.Diswork.models.Tag;
import ru.naumenJavaCourse.WebProject.Diswork.services.StatusService;

@Component
public class StatusValidator implements Validator {

    private final StatusService statusService;

    @Autowired
    public StatusValidator(StatusService statusService) {
        this.statusService = statusService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Status.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Status status = (Status) target;
        if (statusService.findByName(status.getStatusName()).isPresent())
            errors.rejectValue("statusName","","Статус с таким названием уже существует");
    }
}
