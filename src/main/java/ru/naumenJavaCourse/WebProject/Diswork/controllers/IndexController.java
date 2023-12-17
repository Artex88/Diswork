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

import java.util.*;

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
    public String index(Model model, @RequestParam(name = "order", required = false) String order){
        String finalOrder = order;
        if (order == null || !Arrays.stream(Media.class.getDeclaredFields()).filter(field -> field.getType().isPrimitive() || field.getType().equals(String.class)).anyMatch(field -> field.getName().equals(finalOrder)))
            order = "rating";
        List<Media> mediaList = mediaService.getAllMediaAndSort(order);
        model.addAttribute("medias", mediaList);
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
        model.addAttribute("avgRating", media.getRating());
        return "public/mediaDisplay";
    }
}
