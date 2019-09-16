package com.codegym.tnlapartmentsbe.service;

import com.codegym.tnlapartmentsbe.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    List<Comment> findAllByApartmentId(Long id);
    void createComment(Comment comment);
}
