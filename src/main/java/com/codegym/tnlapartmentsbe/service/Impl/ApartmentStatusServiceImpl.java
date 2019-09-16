package com.codegym.tnlapartmentsbe.service.Impl;

import com.codegym.tnlapartmentsbe.model.ApartmentStatus;
import com.codegym.tnlapartmentsbe.repository.ApartmentStatusRepository;
import com.codegym.tnlapartmentsbe.service.ApartmentStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartmentStatusServiceImpl implements ApartmentStatusService {
    @Autowired
    private ApartmentStatusRepository apartmentStatusRepository;


    @Override
    public List<ApartmentStatus> findAll() {
        return apartmentStatusRepository.findAll();
    }

    @Override
    public ApartmentStatus findById(Long id) {
        return apartmentStatusRepository.findById(id).get();
    }

    @Override
    public void save(ApartmentStatus apartmentStatus) {
        apartmentStatusRepository.save(apartmentStatus);

    }

    @Override
    public void deleteById(Long id) {
        apartmentStatusRepository.deleteById(id);

    }

    @Override
    public List<ApartmentStatus> findAllByApartmentId(Long apartmentId) {
        return apartmentStatusRepository.findAllByApartmentId(apartmentId);
    }
}
