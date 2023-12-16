package ru.naumenJavaCourse.WebProject.Diswork.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.naumenJavaCourse.WebProject.Diswork.models.Media;
import ru.naumenJavaCourse.WebProject.Diswork.models.Status;
import ru.naumenJavaCourse.WebProject.Diswork.models.Tag;
import ru.naumenJavaCourse.WebProject.Diswork.models.Type;
import ru.naumenJavaCourse.WebProject.Diswork.services.*;
import ru.naumenJavaCourse.WebProject.Diswork.util.MediaValidator;
import ru.naumenJavaCourse.WebProject.Diswork.util.StatusValidator;
import ru.naumenJavaCourse.WebProject.Diswork.util.TagValidator;
import ru.naumenJavaCourse.WebProject.Diswork.util.TypeValidator;

@Controller
@Secured("ROLE_ADMIN")
public class AdminController {
    private final HttpServletRequest request;
    private final UserService userService;

    private final StatusService statusService;

    private final StatusValidator statusValidator;

    private final MediaService mediaService;

    private final MediaValidator mediaValidator;

    private final TagService tagService;

    private final TagValidator tagValidator;

    private final TypeService typeService;

    private final TypeValidator typeValidator;
    @Autowired
    public AdminController(HttpServletRequest request, UserService userService, StatusService statusService, StatusValidator statusValidator, MediaService mediaService, TagService tagService, TagValidator tagValidator, MediaValidator mediaValidator, TypeService typeService, TypeValidator typeValidator) {
        this.request = request;
        this.userService = userService;
        this.statusService = statusService;
        this.statusValidator = statusValidator;
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
        if (bindingResult.hasErrors())
            return "admin/createType";
        typeValidator.validate(type,bindingResult);

        typeService.save(type);
        return "redirect:/admin/adminPage";
    }

    @GetMapping("/admin/newTag")
    public String newTag(@ModelAttribute("tag") Tag tag){
        return "admin/createTag";
    }

    @PostMapping("/admin/createTag")
    public String createTag(@ModelAttribute("tag") @Valid Tag tag, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "admin/createTag";
        tagValidator.validate(tag,bindingResult);

        tagService.save(tag);
        return "redirect:/admin/adminPage";
    }

    @GetMapping("/admin/newStatus")
    public String newStatus(@ModelAttribute("status") Status status){
        return "admin/createStatus";
    }

    @PostMapping("/admin/createStatus")
    public String createTag(@ModelAttribute("status") @Valid Status status, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "admin/createStatus";
        statusValidator.validate(status,bindingResult);

        statusService.save(status);
        return "redirect:/admin/adminPage";
    }

    @GetMapping("/admin/newMedia")
    public String newMedia(@ModelAttribute("media") Media media, Model model){
        model.addAttribute("tagList", tagService.getAll());
        model.addAttribute("typeList", typeService.getAll());
        model.addAttribute("statusList", statusService.getAll());
        return "admin/createMedia";
    }

    @PostMapping("/admin/createMedia")
    public String createMedia(@ModelAttribute("media") @Valid Media media, @RequestPart(name = "imageFile") MultipartFile imageFile, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "admin/createMedia";
        mediaValidator.validate(media, bindingResult);

        mediaService.save(media, imageFile);
        return "redirect:/admin/adminPage";
    }
}
