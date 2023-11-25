package ru.naumenJavaCourse.WebProject.Diswork.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.naumenJavaCourse.WebProject.Diswork.models.Media;
import ru.naumenJavaCourse.WebProject.Diswork.models.Tag;
import ru.naumenJavaCourse.WebProject.Diswork.models.Type;
import ru.naumenJavaCourse.WebProject.Diswork.services.MediaService;
import ru.naumenJavaCourse.WebProject.Diswork.services.TagService;
import ru.naumenJavaCourse.WebProject.Diswork.services.TypeService;
import ru.naumenJavaCourse.WebProject.Diswork.services.UserService;
import ru.naumenJavaCourse.WebProject.Diswork.util.MediaValidator;
import ru.naumenJavaCourse.WebProject.Diswork.util.TagValidator;
import ru.naumenJavaCourse.WebProject.Diswork.util.TypeValidator;

@Controller
public class AdminController {
    private final HttpServletRequest request;
    private final UserService userService;

    private final MediaService mediaService;

    private final MediaValidator mediaValidator;

    private final TagService tagService;

    private final TagValidator tagValidator;

    private final TypeService typeService;

    private final TypeValidator typeValidator;
    @Autowired
    public AdminController(HttpServletRequest request, UserService userService, MediaService mediaService, TagService tagService, TagValidator tagValidator, MediaValidator mediaValidator, TypeService typeService, TypeValidator typeValidator) {
        this.request = request;
        this.userService = userService;
        this.mediaService = mediaService;
        this.tagService = tagService;
        this.tagValidator = tagValidator;
        this.mediaValidator = mediaValidator;
        this.typeService = typeService;
        this.typeValidator = typeValidator;
    }

    @GetMapping("/admin/adminPage")
    public ModelAndView showAdminPage(){
        int id = (int) request.getSession().getAttribute("id");
        return new ModelAndView("/admin/adminPage", "user", userService.findById((id)));
    }

    @GetMapping("/admin/newType")
    public String newType(@ModelAttribute("type") Type type){
        return "admin/createType";
    }

    @PostMapping("/admin/createType")
    public String createTag(@ModelAttribute("type") @Valid Type type, BindingResult bindingResult){
        typeValidator.validate(type,bindingResult);
        if (bindingResult.hasErrors())
            return "admin/createType";

        typeService.save(type);
        return "redirect:/admin/adminPage";
    }

    @GetMapping("/admin/newTag")
    public String newTag(@ModelAttribute("tag") Tag tag){
        return "admin/createTag";
    }

    @PostMapping("/admin/createTag")
    public String createTag(@ModelAttribute("tag") @Valid Tag tag, BindingResult bindingResult){
        tagValidator.validate(tag,bindingResult);
        if (bindingResult.hasErrors())
            return "admin/createTag";

        tagService.save(tag);
        return "redirect:/admin/adminPage";
    }

    @GetMapping("/admin/newMedia")
    public String newMedia(@ModelAttribute("media") Media media, Model model){
        model.addAttribute("tagList", tagService.getAll());
        model.addAttribute("typeList", typeService.getAll());
        return "admin/createMedia";
    }

    @PostMapping("/admin/createMedia")
    public String createMedia(@ModelAttribute("media") @Valid Media media, @RequestPart(name = "imageFile") MultipartFile imageFile, BindingResult bindingResult){
        mediaValidator.validate(media, bindingResult);
        if (bindingResult.hasErrors())
            return "admin/createMedia";

        mediaService.save(media, imageFile);
        return "redirect:/admin/adminPage";
    }
}
