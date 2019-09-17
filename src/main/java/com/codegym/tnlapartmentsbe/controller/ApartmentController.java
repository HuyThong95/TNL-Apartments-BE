package com.codegym.tnlapartmentsbe.controller;

import com.codegym.tnlapartmentsbe.form.response.StandardResponse;
import com.codegym.tnlapartmentsbe.model.Apartment;
import com.codegym.tnlapartmentsbe.model.ApartmentOrders;
import com.codegym.tnlapartmentsbe.model.ImagesOfApartment;
import com.codegym.tnlapartmentsbe.security.service.UserPrinciple;
import com.codegym.tnlapartmentsbe.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ApartmentController {
    @Autowired
    private ApartmentService apartmentService;
    @Autowired
    private ImagesOfApartmentService imagesOfApartmentService;
    @Autowired
    private ApartmentOrdersService apartmentOrdersService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RateService rateService;
    @Autowired
    private ApartmentStatusService apartmentStatusService;

    private UserPrinciple getCurrentUser() {
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

    @RequestMapping(value = "/apartment", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<StandardResponse> listAllApartments() {
        List<Apartment> apartments = this.apartmentService.findAll();

        if (apartments.isEmpty()) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found data", null),
                    HttpStatus.OK);
        }

        for (Apartment apartment : apartments) {
            List<String> listImageUrlOfApartment = imagesOfApartmentService.getListImageUrlOfApartmentByApartmentId(apartment.getId());
            apartment.setImageUrls(listImageUrlOfApartment);
            List<ApartmentOrders> apartmentOrders = apartmentOrdersService.findApartmentOrdersByApartmentId(apartment.getId());
            apartment.setApartmentOrders(apartmentOrders);
        }

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Successfully. Get list all apartments", apartments),
                HttpStatus.OK);
    }

    @RequestMapping(value = "apartment/{id}", method = RequestMethod.GET)
    public ResponseEntity<StandardResponse> getApartmentDetails(@PathVariable Long id){
        Apartment apartment = apartmentService.findById(id);
        if (apartment == null){
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "fail, not found apartment", null),
                    HttpStatus.OK
            );
        }
        List<String> imageUrlOfApartment = imagesOfApartmentService.getListImageUrlOfApartmentByApartmentId(apartment.getId());
        apartment.setImageUrls(imageUrlOfApartment);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Apartment Details", apartment),
                HttpStatus.OK);
    }






}
