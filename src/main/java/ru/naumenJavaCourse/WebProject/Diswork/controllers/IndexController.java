package ru.naumenJavaCourse.WebProject.Diswork.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.naumenJavaCourse.WebProject.Diswork.dto.GradingDTO;
import ru.naumenJavaCourse.WebProject.Diswork.models.Media;
import ru.naumenJavaCourse.WebProject.Diswork.models.User;
import ru.naumenJavaCourse.WebProject.Diswork.services.MediaService;
import ru.naumenJavaCourse.WebProject.Diswork.services.UserService;

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

    @GetMapping("/media/{mediaId}")
    public String displayMedia(@PathVariable int mediaId, Model model, HttpServletRequest request){
        User user = userService.findByAttributeSessionId(request.getSession().getAttribute("id"));
        boolean isMediaExistInUserList = false;
        if(user == null)
            model.addAttribute("isAddButtonHidden", "false");
        else {
            if (userService.isMediaExistInUserList(user, mediaId)) {
                isMediaExistInUserList = true;
                model.addAttribute("isAddButtonHidden", "true");
            }
            else
                model.addAttribute("isAddButtonHidden", "false");
        }

        Media media = mediaService.findById(mediaId);
        int grade = 0;
        if (isMediaExistInUserList) {
            grade = userService.getCertainUserMedia(user, user.getId(), mediaId).getGrade();
        }

        model.addAttribute("grade", grade);
        model.addAttribute("media", media);
        model.addAttribute("grading", new GradingDTO());
        model.addAttribute("avgRating", mediaService.getAvgRating(mediaId));
        return "public/mediaDisplay";
    }
}
