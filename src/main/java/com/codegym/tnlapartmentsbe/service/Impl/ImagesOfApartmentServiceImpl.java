package com.codegym.tnlapartmentsbe.service.Impl;

import com.codegym.tnlapartmentsbe.model.ImagesOfApartment;
import com.codegym.tnlapartmentsbe.repository.ImagesOfApartmentRepository;
import com.codegym.tnlapartmentsbe.service.ImagesOfApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImagesOfApartmentServiceImpl implements ImagesOfApartmentService {
    @Autowired
    private ImagesOfApartmentRepository imagesOfApartmentRepository;

    @Override
    public List<ImagesOfApartment> findAll() {
        return imagesOfApartmentRepository.findAll();
    }

    @Override
    public List<ImagesOfApartment> findByApartmentId(Long id) {
        return imagesOfApartmentRepository.findByApartmentId(id);
    }

    @Override
    public List<String> getListImageUrlOfApartmentByApartmentId(Long id) {
        List<String> listImageUrl=new ArrayList<>();
        List<ImagesOfApartment> imagesOfApartments = imagesOfApartmentRepository.findByApartmentId(id);
        for (ImagesOfApartment image:imagesOfApartments){
            listImageUrl.add(image.getImageUrl());
        }
        return listImageUrl;
    }

    @Override
    public ImagesOfApartment findById(Long id) {
        return imagesOfApartmentRepository.findById(id).get();
    }

    @Override
    public void createImage(ImagesOfApartment imagesOfApartment) {
        imagesOfApartmentRepository.save(imagesOfApartment);

    }

    @Override
    public void updateImage(ImagesOfApartment imagesOfApartment) {
        imagesOfApartmentRepository.save(imagesOfApartment);

    }

    @Override
    public void deleteImage(Long id) {
        imagesOfApartmentRepository.deleteById(id);

    }
}
