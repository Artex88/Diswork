package ru.naumenJavaCourse.WebProject.Diswork.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.naumenJavaCourse.WebProject.Diswork.models.User;
import ru.naumenJavaCourse.WebProject.Diswork.services.MediaService;
import ru.naumenJavaCourse.WebProject.Diswork.services.UserService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/index")
public class IndexController {

    private final MediaService mediaService;

    private final UserService userService;

    @Autowired
    public IndexController(MediaService mediaService, UserService userService) {
        this.mediaService = mediaService;
        this.userService = userService;
    }


    @GetMapping()
    public String index(Model model){
        model.addAttribute("medias", mediaService.getAll());
        return "public/index";
    }

    @GetMapping("/media/{id}")
    public ModelAndView displayMedia(@PathVariable int id){
        return new ModelAndView("public/mediaDisplay", "media", mediaService.findById(id));
    }
}
