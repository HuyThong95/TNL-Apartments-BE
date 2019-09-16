package com.codegym.tnlapartmentsbe.repository;

import com.codegym.tnlapartmentsbe.model.ImagesOfApartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagesOfApartmentRepository extends JpaRepository<ImagesOfApartment, Long> {
    List<ImagesOfApartment> findByApartmentId(Long id);

}
