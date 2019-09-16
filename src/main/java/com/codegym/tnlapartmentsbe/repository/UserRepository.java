package com.codegym.tnlapartmentsbe.repository;

import com.codegym.tnlapartmentsbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin(origins = "*", maxAge = 3600)
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findUserById(long id);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
}
