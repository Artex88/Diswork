package ru.naumenJavaCourse.WebProject.Diswork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumenJavaCourse.WebProject.Diswork.models.Comment;
import ru.naumenJavaCourse.WebProject.Diswork.repositories.CommentRepository;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void save(Comment comment){
        commentRepository.save(comment);
    }
}
