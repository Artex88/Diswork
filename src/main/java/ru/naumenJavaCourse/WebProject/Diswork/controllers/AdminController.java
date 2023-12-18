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

import java.util.List;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/admin")
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

    @GetMapping("/adminPage")
    public ModelAndView showAdminPage(){
        int id = (int) request.getSession().getAttribute("id");
        return new ModelAndView("/admin/adminPage", "user", userService.findById((id)));
    }

    @GetMapping("/newType")
    public String newType(@ModelAttribute("type") Type type){
        return "admin/createType";
    }

    @PostMapping("/createType")
    public String createTag(@ModelAttribute("type") @Valid Type type, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "admin/createType";
        typeValidator.validate(type,bindingResult);

        typeService.save(type);
        return "redirect:/admin/adminPage";
    }

    @GetMapping("/newTag")
    public String newTag(@ModelAttribute("tag") Tag tag){
        return "admin/createTag";
    }

    @PostMapping("/createTag")
    public String createTag(@ModelAttribute("tag") @Valid Tag tag, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "admin/createTag";
        tagValidator.validate(tag,bindingResult);

        tagService.save(tag);
        return "redirect:/admin/adminPage";
    }

    @GetMapping("/newStatus")
    public String newStatus(@ModelAttribute("status") Status status){
        return "admin/createStatus";
    }

    @PostMapping("/createStatus")
    public String createTag(@ModelAttribute("status") @Valid Status status, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "admin/createStatus";
        statusValidator.validate(status,bindingResult);

        statusService.save(status);
        return "redirect:/admin/adminPage";
    }

    @GetMapping("/newMedia")
    public String newMedia(@ModelAttribute("media") Media media, Model model){
        model.addAttribute("tagList", tagService.getAll());
        model.addAttribute("typeList", typeService.getAll());
        model.addAttribute("statusList", statusService.getAll());
        return "admin/createMedia";
    }

    @PostMapping("/createMedia")
    public String createMedia(@ModelAttribute("media") @Valid Media media, @RequestPart(name = "imageFile") MultipartFile imageFile, BindingResult bindingResult){
        if (bindingResult.hasErrors() || imageFile == null)
            return "admin/createMedia";
        mediaValidator.validate(media, bindingResult);

        mediaService.save(media, imageFile);
        return "redirect:/admin/adminPage";
    }

    @GetMapping("/allMedia")
    public String getAllMedia(Model model){
        model.addAttribute("medias", mediaService.getAll());
        return "admin/allMedia";
    }

    @GetMapping("/allTags")
    public String getAllTags(Model model){
        model.addAttribute("tags", tagService.getAll());
        return "admin/allTags";
    }

    @GetMapping("/allTypes")
    public String getAllTypes(Model model){
        model.addAttribute("types", typeService.getAll());
        return "admin/allTypes";
    }

    @GetMapping("/allStatuses")
    public String getAllStatuses(Model model){
        model.addAttribute("statuses", statusService.getAll());
        return "admin/allStatuses";
    }

    @GetMapping("/media/{id}")
    public String showMedia(@PathVariable("id") int mediaId, Model model){
        Media media = mediaService.findById(mediaId);
        model.addAttribute("media", media);
        model.addAttribute("tagList", tagService.getAll());
        model.addAttribute("statusList", statusService.getAll());
        model.addAttribute("typeList", typeService.getAll());
        return "admin/editMedia";
    }

    @GetMapping("/status/{id}")
    public String showStatus(@PathVariable("id") int statusId, Model model){
        Status status = statusService.findById(statusId);
        model.addAttribute("status", status);
        return "admin/editStatus";
    }

    @GetMapping("/tag/{id}")
    public String showTag(@PathVariable("id") int tagId, Model model){
        Tag tag = tagService.findById(tagId);
        model.addAttribute("tag", tag);
        return "admin/editTag";
    }

    @GetMapping("/type/{id}")
    public String showType(@PathVariable("id") int typeId, Model model){
        Type type = typeService.findById(typeId);
        model.addAttribute("type", type);
        return "admin/editType";
    }

    @PatchMapping("/media/{id}")
    public String updateMedia(@RequestPart(name = "imageFile") MultipartFile multipartFile, @PathVariable("id") int mediaId, @ModelAttribute("media") @Valid Media media, BindingResult bindingResult ){
        if (bindingResult.hasErrors())
            return "admin/editMedia";
        mediaService.upload(media, mediaId, multipartFile);
        return "redirect:/admin/allMedia";
    }

    @PatchMapping("/status/{id}")
    public String updateStatus(@ModelAttribute("status") @Valid Status status, @PathVariable("id") int statusId, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "admin/editStatus";
        statusService.upload(status,statusId);
        return "redirect:/admin/allStatuses";
    }

    @PatchMapping("/tag/{id}")
    public String updateTag(@ModelAttribute("tag") @Valid Tag tag, @PathVariable("id") int tagId, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "admin/editTag";
        tagService.upload(tag, tagId);
        return "redirect:/admin/allTags";
    }

    @PatchMapping("/type/{id}")
    public String updateType(@ModelAttribute("type") @Valid Type type, @PathVariable("id") int typeId, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "admin/editType";
        typeService.upload(type, typeId);
        return "redirect:/admin/allTypes";
    }

    @DeleteMapping("/media/{id}")
    public String deleteMedia(@PathVariable("id") int mediaId){
        mediaService.delete(mediaId);
        return "redirect:/admin/allMedia";
    }

    @DeleteMapping("/tag/{id}")
    public String deleteTag(@PathVariable("id") int tagId){
        tagService.delete(tagId);
        return "redirect:/admin/allTags";
    }

    @DeleteMapping("/status/{id}")
    public String deleteStatus(@PathVariable("id") int statusId){
        statusService.delete(statusId);
        return "redirect:/admin/allStatuses";
    }

    @DeleteMapping("/type/{id}")
    public String deleteType(@PathVariable("id") int typeId){
        typeService.delete(typeId);
        return "redirect:/admin/allTypes";
    }
}
