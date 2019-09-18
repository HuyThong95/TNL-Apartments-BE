package com.codegym.tnlapartmentsbe.service;

import com.codegym.tnlapartmentsbe.model.Apartment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApartmentService {
    List<Apartment> findAll();
    List<Apartment> findByUserId( Long userId);
    Apartment findById(Long id);
    void createApartment(Apartment apartment);
    void updateApartment(Apartment apartment);
    void deleteApartment(Long id);
}
