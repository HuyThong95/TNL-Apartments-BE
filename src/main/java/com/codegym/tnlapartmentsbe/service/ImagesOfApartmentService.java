package com.codegym.tnlapartmentsbe.service;

import com.codegym.tnlapartmentsbe.model.ImagesOfApartment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImagesOfApartmentService {
    List<ImagesOfApartment> findAll();
    List<ImagesOfApartment> findByApartmentId(Long id);
    List<String> getListImageUrlOfApartmentByApartmentId(Long id);
    ImagesOfApartment findById(Long id);
    void createImage(ImagesOfApartment imagesOfApartment);
    void updateImage(ImagesOfApartment imagesOfApartment);
    void deleteImage(Long id);

}
