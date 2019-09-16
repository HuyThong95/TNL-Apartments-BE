package com.codegym.tnlapartmentsbe.repository;

import com.codegym.tnlapartmentsbe.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
}
