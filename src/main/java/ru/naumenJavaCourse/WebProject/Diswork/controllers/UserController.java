package ru.naumenJavaCourse.WebProject.Diswork.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.naumenJavaCourse.WebProject.Diswork.services.UserService;

@Controller
public class UserController {

    private final HttpServletRequest request;
    private final UserService userService;
    @Autowired
    public UserController(HttpServletRequest request, UserService userService) {
        this.request = request;
        this.userService = userService;
    }

    @GetMapping("/user/userPage")
    public ModelAndView showUserPage()
    {
        int id = (int) request.getSession().getAttribute("id");
        return new ModelAndView("/user/userPage", "user", userService.findById(id));
    }
}
