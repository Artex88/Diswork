package ru.naumenJavaCourse.WebProject.Diswork.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.naumenJavaCourse.WebProject.Diswork.models.Comment;
import ru.naumenJavaCourse.WebProject.Diswork.models.Media;
import ru.naumenJavaCourse.WebProject.Diswork.services.CommentService;
import ru.naumenJavaCourse.WebProject.Diswork.services.UserService;

import java.time.LocalDateTime;

@Controller
public class UserController {

    private final HttpServletRequest request;
    private final UserService userService;

    private final CommentService commentService;
    @Autowired
    public UserController(HttpServletRequest request, UserService userService, CommentService commentService) {
        this.request = request;
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping("/user/userPage")
    public ModelAndView showUserPage()
    {
        int id = (int) request.getSession().getAttribute("id");
        return new ModelAndView("/user/userPage", "user", userService.findById(id));
    }
    @GetMapping("/user/media")
    public ModelAndView showUserMedia()
    {
        int id = (int) request.getSession().getAttribute("id");
        return new ModelAndView("/user/userMedia", "userMediaList", userService.showUserMedia(id));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/user/comment/add")
    public String addCommentToMedia(@ModelAttribute("commentForm") @Valid Comment comment){
        int userId = (int) request.getSession().getAttribute("id");
        comment.setUser(userService.findById(userId));
        comment.setLocalDateTime(LocalDateTime.now());
        commentService.save(comment);
        return String.format("redirect:/index/media/%s", comment.getMedia().getId());
    }
}
