package ru.naumenJavaCourse.WebProject.Diswork.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PagesController {
    @GetMapping("/default")
    public String pagesRedirect(HttpServletRequest request){
        if (request.isUserInRole("ADMIN"))
            return "redirect:/pages/adminPage";
        else
            return "redirect:/pages/userPage";
    }
    @GetMapping("/pages/userPage")
    public String showUserPage(){
        return "/pages/userPage";
    }
    @GetMapping("/pages/adminPage")
    public String showAdminPage(){
        return "/pages/adminPage";
    }
}
