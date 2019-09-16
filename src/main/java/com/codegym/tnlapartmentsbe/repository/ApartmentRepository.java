package com.codegym.tnlapartmentsbe.repository;

import com.codegym.tnlapartmentsbe.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", maxAge = 3600)
@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
}
