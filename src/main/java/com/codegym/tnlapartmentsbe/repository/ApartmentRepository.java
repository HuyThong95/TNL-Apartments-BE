package com.codegym.tnlapartmentsbe.repository;

import com.codegym.tnlapartmentsbe.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
    List<Apartment> findByUserId( Long userId);
    Apartment findApartmentByName(String name);
}
