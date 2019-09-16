package com.codegym.tnlapartmentsbe.repository;

import com.codegym.tnlapartmentsbe.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByApartmentId(Long apartmentId);

}
