package com.codegym.tnlapartmentsbe.service;

import com.codegym.tnlapartmentsbe.model.Rate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RateService {
    List<Rate> findAllByApartmentId(Long apartmentId);

    void createRate(Rate rate);

    boolean existsRateByUserIdAndApartmentId (Long id, Long apartmentId);

    Rate findByUserIdAndApartmentId(Long userId, Long apartmentId);
}
