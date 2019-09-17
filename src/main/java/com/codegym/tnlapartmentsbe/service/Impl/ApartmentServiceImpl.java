package com.codegym.tnlapartmentsbe.service.Impl;

import com.codegym.tnlapartmentsbe.model.Apartment;
import com.codegym.tnlapartmentsbe.repository.ApartmentRepository;
import com.codegym.tnlapartmentsbe.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartmentServiceImpl implements ApartmentService {
    @Autowired
    private ApartmentRepository apartmentRepository;

    @Override
    public List<Apartment> findAll() {
        return apartmentRepository.findAll();
    }

    @Override
    public List<Apartment> findByUserId(Long userId) {
        return apartmentRepository.findByUserId(userId);
    }

    @Override
    public Apartment findById(Long id) {
        return apartmentRepository.findById(id).get();
    }

    @Override
    public void createApartment(Apartment apartment) {
        apartmentRepository.save(apartment);

    }

    @Override
    public void updateApartment(Apartment apartment) {
        apartmentRepository.save(apartment);

    }

    @Override
    public void deleteApartment(Long id) {
        apartmentRepository.deleteById(id);

    }
}
