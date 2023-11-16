package ru.naumenJavaCourse.WebProject.Diswork.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.naumenJavaCourse.WebProject.Diswork.models.Media;
import ru.naumenJavaCourse.WebProject.Diswork.models.Tag;
import ru.naumenJavaCourse.WebProject.Diswork.services.MediaService;
import ru.naumenJavaCourse.WebProject.Diswork.services.TagService;
import ru.naumenJavaCourse.WebProject.Diswork.services.UserService;
import ru.naumenJavaCourse.WebProject.Diswork.util.MediaValidator;
import ru.naumenJavaCourse.WebProject.Diswork.util.TagValidator;

@Controller
public class AdminController {
    private final HttpServletRequest request;
    private final UserService userService;

    private final MediaService mediaService;

    private final TagService tagService;

    private final TagValidator tagValidator;

    private final MediaValidator mediaValidator;
    @Autowired
    public AdminController(HttpServletRequest request, UserService userService, MediaService mediaService, TagService tagService, TagValidator tagValidator, MediaValidator mediaValidator) {
        this.request = request;
        this.userService = userService;
        this.mediaService = mediaService;
        this.tagService = tagService;
        this.tagValidator = tagValidator;
        this.mediaValidator = mediaValidator;
    }

    @GetMapping("/admin/adminPage/")
    public ModelAndView showAdminPage(){
        int id = (int) request.getSession().getAttribute("id");
        return new ModelAndView("/admin/adminPage", "user", userService.findById((id)));
    }

    @GetMapping("/admin/newTag/")
    public String newTag(@ModelAttribute("tag") Tag tag){
        return "admin/createTag";
    }

    @PostMapping("/admin/createTag/")
    public String createTag(@ModelAttribute("tag") @Valid Tag tag, BindingResult bindingResult){
        tagValidator.validate(tag,bindingResult);
        if (bindingResult.hasErrors())
            return "admin/createTag";

        tagService.save(tag);
        return "redirect:/admin/adminPage/";
    }

    @GetMapping("/admin/newMedia")
    public String newMedia(@ModelAttribute("media") Media media, Model model){
        model.addAttribute("tagList", tagService.getAll());
        return "admin/createMedia";
    }

    @PostMapping("/admin/createMedia")
    public String createMedia(@ModelAttribute("media") @Valid Media media, BindingResult bindingResult){
        mediaValidator.validate(media, bindingResult);
        if (bindingResult.hasErrors())
            return "admin/createMedia";

        mediaService.save(media);
        return "redirect:/admin/adminPage/";
    }
}
