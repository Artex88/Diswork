package ru.naumenJavaCourse.WebProject.Diswork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.naumenJavaCourse.WebProject.Diswork.services.MediaService;

@Controller
@RequestMapping("/index")
public class IndexController {

    private final MediaService mediaService;

    @Autowired
    public IndexController(MediaService mediaService) {
        this.mediaService = mediaService;
    }


    @GetMapping()
    public String index(Model model){
        model.addAttribute("medias", mediaService.getAll());
        return "index";
    }
}
