package com.codegym.tnlapartmentsbe.service.Impl;

import com.codegym.tnlapartmentsbe.model.Comment;
import com.codegym.tnlapartmentsbe.repository.CommentRepository;
import com.codegym.tnlapartmentsbe.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> findAllByApartmentId(Long id) {
        return commentRepository.findAllByApartmentId(id);
    }

    @Override
    public void createComment(Comment comment) {
        commentRepository.save(comment);

    }
}
