package com.codegym.tnlapartmentsbe.repository;

import com.codegym.tnlapartmentsbe.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
    List<Rate> findAllByApartmentId(Long id);
    boolean existsRateByUserIdAndApartmentId(Long id, Long apartmentId);
    Rate findByUserIdAndApartmentId(Long userId, Long apartmentId);
}
