package ru.naumenJavaCourse.WebProject.Diswork.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.naumenJavaCourse.WebProject.Diswork.models.Tag;
import ru.naumenJavaCourse.WebProject.Diswork.services.TagService;
import ru.naumenJavaCourse.WebProject.Diswork.services.UserService;
import ru.naumenJavaCourse.WebProject.Diswork.util.TagValidator;

@Controller
public class AdminController {
    private final HttpServletRequest request;
    private final UserService userService;

    private final TagService tagService;

    private final TagValidator tagValidator;
    @Autowired
    public AdminController(HttpServletRequest request, UserService userService, TagService tagService, TagValidator tagValidator) {
        this.request = request;
        this.userService = userService;
        this.tagService = tagService;
        this.tagValidator = tagValidator;
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
}
