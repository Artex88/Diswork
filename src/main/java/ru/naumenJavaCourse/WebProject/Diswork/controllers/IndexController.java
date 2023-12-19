package ru.naumenJavaCourse.WebProject.Diswork.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.naumenJavaCourse.WebProject.Diswork.dto.GradingDTO;
import ru.naumenJavaCourse.WebProject.Diswork.models.*;
import ru.naumenJavaCourse.WebProject.Diswork.services.*;

import java.util.*;

@Controller
@RequestMapping("/index")
public class IndexController {

    private final MediaService mediaService;

    private final UserService userService;

    private final TypeService typeService;

    private final TagService tagService;

    private final StatusService statusService;

    @Autowired
    public IndexController(MediaService mediaService, UserService userService, TypeService typeService, TagService tagService, StatusService statusService) {
        this.mediaService = mediaService;
        this.userService = userService;
        this.typeService = typeService;
        this.tagService = tagService;
        this.statusService = statusService;
    }


    @GetMapping()
    public String index(Model model,
                        @RequestParam(name = "order", required = false) String order,
                        @RequestParam(required = false) Integer typeId,
                        @RequestParam(required = false) Set<Integer> tagIds,
                        @RequestParam(required = false) Integer statusId,
                        @RequestParam(required = false) String episodeDuration,
                        @RequestParam(required = false) String releasePeriod){
        String finalOrder = order;
        Set<Tag> tagSet = (tagIds == null || tagIds.isEmpty()) ? Collections.emptySet() : tagService.getTagsListFromIds(tagIds);
        Type type = (typeId == null) ? null : typeService.findById(typeId);
        Status status = (statusId == null) ? null : statusService.findById(statusId);

        if (order == null || !Arrays.stream(Media.class.getDeclaredFields()).filter(field -> field.getType().isPrimitive() || field.getType().equals(String.class)).anyMatch(field -> field.getName().equals(finalOrder)))
            order = "rating";
        List<Media> mediaList = mediaService.getFilterMediaAndSort(type, status, episodeDuration, releasePeriod, tagSet, order);
        model.addAttribute("medias", mediaList);
        model.addAttribute("tags", tagService.getAll());
        model.addAttribute("statuses", statusService.getAll());
        model.addAttribute("types", typeService.getAll());
        model.addAttribute("episodeDurations", List.of("короткий", "средний", "длинный"));
        model.addAttribute("releasePeriods", List.of(
                "более старые", "1930e годы", "1940e годы", "1950e годы", "1960e годы", "1970e годы",
                "1980e годы", "1990e годы", "2000e годы", "2010e годы","2020 год","2021 год","2022 год","2023 год", "2024 год"));
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
