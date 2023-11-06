package ru.naumenJavaCourse.WebProject.Diswork.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.naumenJavaCourse.WebProject.Diswork.models.User;
import ru.naumenJavaCourse.WebProject.Diswork.services.RegistrationService;
import ru.naumenJavaCourse.WebProject.Diswork.services.UserValidateService;
import ru.naumenJavaCourse.WebProject.Diswork.util.UserValidator;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private  final UserValidator userValidator;
    private  final RegistrationService registrationService;
    @Autowired
    public AuthController(UserValidator userValidator, RegistrationService registrationService) {
        this.userValidator = userValidator;
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User person){
        return "/auth/registration";
    }
    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid User user, BindingResult bindingResult){
        userValidator.validate(user,bindingResult);
        if (bindingResult.hasErrors())
            return "auth/registration";
        registrationService.register(user);
        return "redirect:/auth/login";
    }
}
