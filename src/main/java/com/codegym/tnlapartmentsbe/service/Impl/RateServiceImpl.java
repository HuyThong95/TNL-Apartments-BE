package com.codegym.tnlapartmentsbe.service.Impl;

import com.codegym.tnlapartmentsbe.model.Rate;
import com.codegym.tnlapartmentsbe.repository.RateRepository;
import com.codegym.tnlapartmentsbe.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateServiceImpl implements RateService {
    @Autowired
    private RateRepository rateRepository;

    @Override
    public List<Rate> findAllByApartmentId(Long apartmentId) {
        return rateRepository.findAllByApartmentId(apartmentId);
    }

    @Override
    public void createRate(Rate rate) {
        rateRepository.save(rate);

    }

    @Override
    public boolean existsRateByUserIdAndApartmentId(Long id, Long apartmentId) {
        return rateRepository.existsRateByUserIdAndApartmentId(id, apartmentId);
    }

    @Override
    public Rate findByUserIdAndApartmentId(Long userId, Long apartmentId) {
        return rateRepository.findByUserIdAndApartmentId(userId, apartmentId);
    }
}
