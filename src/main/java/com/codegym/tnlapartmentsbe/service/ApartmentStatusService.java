package com.codegym.tnlapartmentsbe.service;

import com.codegym.tnlapartmentsbe.model.Apartment;
import com.codegym.tnlapartmentsbe.model.ApartmentStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApartmentStatusService {
    List<ApartmentStatus> findAll();
    ApartmentStatus findById(Long id);
    void save(ApartmentStatus apartmentStatus);
    void deleteById(Long id);
    List<ApartmentStatus> findAllByApartmentId( Long apartmentId);

}
