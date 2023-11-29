package ru.naumenJavaCourse.WebProject.Diswork.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.naumenJavaCourse.WebProject.Diswork.dto.UserDTO;
import ru.naumenJavaCourse.WebProject.Diswork.models.User;
import ru.naumenJavaCourse.WebProject.Diswork.services.RegistrationService;
import ru.naumenJavaCourse.WebProject.Diswork.services.UserService;
import ru.naumenJavaCourse.WebProject.Diswork.util.UserValidator;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private  final UserValidator userValidator;
    private  final RegistrationService registrationService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthController(UserValidator userValidator, RegistrationService registrationService, UserService userService, ModelMapper modelMapper) {
        this.userValidator = userValidator;
        this.registrationService = registrationService;
        this.userService = userService;
        this.modelMapper = modelMapper;
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
    public String performRegistration(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult bindingResult){
        User user = this.convertToUser(userDTO);
        userValidator.validate(user,bindingResult);
        if (bindingResult.hasErrors())
            return "auth/registration";
        registrationService.register(user);
        return "redirect:/auth/login";
    }

    @GetMapping("/default")
    public String pagesRedirect(HttpServletRequest request){
        int id = userService.findByUsername(request.getRemoteUser()).getId();
        request.getSession().setAttribute("id", id);
        if (request.isUserInRole("ADMIN"))
            return "redirect:/admin/adminPage";
        else
            return "redirect:/user/userPage";
    }

    public User convertToUser(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }

    public UserDTO convertToUserDTO(User user){
        return modelMapper.map(user, UserDTO.class);
    }
}
