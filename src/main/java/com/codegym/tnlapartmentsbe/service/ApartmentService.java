package com.codegym.tnlapartmentsbe.service;

import com.codegym.tnlapartmentsbe.model.Apartment;
import org.springframework.stereotype.Service;

@Service
public interface ApartmentService {
    Apartment findById(Long id);
    void createApartment(Apartment apartment);
    void updateApartment(Apartment apartment);
    void deleteApartment(Long id);
}
