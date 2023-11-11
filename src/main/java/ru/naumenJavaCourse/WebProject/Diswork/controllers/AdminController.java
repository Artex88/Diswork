package ru.naumenJavaCourse.WebProject.Diswork.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.naumenJavaCourse.WebProject.Diswork.services.UserService;

@Controller
public class AdminController {
    private final HttpServletRequest request;
    private final UserService userService;
    @Autowired
    public AdminController(HttpServletRequest request, UserService userService) {
        this.request = request;
        this.userService = userService;
    }

    @GetMapping("/admin/adminPage/")
    public ModelAndView showAdminPage(){
        int id = (int) request.getSession().getAttribute("id");
        return new ModelAndView("/admin/adminPage", "user", userService.findById((id)));
    }
}