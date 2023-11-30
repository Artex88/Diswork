package ru.naumenJavaCourse.WebProject.Diswork.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.naumenJavaCourse.WebProject.Diswork.dto.GradingDTO;
import ru.naumenJavaCourse.WebProject.Diswork.dto.MediaJsonDto;
import ru.naumenJavaCourse.WebProject.Diswork.services.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class JsController {
    private final UserService userService;

    @Autowired
    public JsController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("isAuthenticated()")
    @Secured("ROLE_USER")
    @PostMapping("/index/media/add")
    public Map<String, String> addMediaToUserList(@RequestBody MediaJsonDto mediaJsonDto, HttpServletRequest request){
        Map<String, String> data = new HashMap<>();
        int userId = (int) request.getSession().getAttribute("id");
        String response = userService.addMediaToList(userId, Integer.parseInt(mediaJsonDto.getId()));
        data.put("message", response);
        return data;
    }
    @PreAuthorize("isAuthenticated()")
    @Secured("ROLE_USER")
    @PostMapping("/index/media/delete")
    public Map<String, String> deleteMediaFromUserList(@RequestBody MediaJsonDto mediaJsonDto, HttpServletRequest request){
        Map<String, String> data = new HashMap<>();
        int userId = (int) request.getSession().getAttribute("id");
        String response = userService.deleteMediaFromList(userId, Integer.parseInt(mediaJsonDto.getId()));
        data.put("message", response);
        return data;
    }
    @PreAuthorize("isAuthenticated()")
    @Secured("ROLE_USER")
    @PostMapping("/index/media/assessment")
    public Map<String, String> assessmentMedia(@RequestBody GradingDTO gradingDTO, HttpServletRequest request){
        Map<String, String> data = new HashMap<>();
        int userId = (int) request.getSession().getAttribute("id");
        String response = userService.assessmentMedia(userId, Integer.parseInt(gradingDTO.getMediaId()), Integer.parseInt(gradingDTO.getGrade()));
        data.put("message", response);
        return data;
    }
}
