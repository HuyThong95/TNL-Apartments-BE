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

@CrossOrigin(origins = "*")
@RestController
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
    private UserPrinciple getCurrentUser(){
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @RequestMapping( value = "/apartments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<StandardResponse> listApartments(){
        List<Apartment> apartments = this.apartmentService.findAll();
        if (apartments.isEmpty()){
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Not found Apartment", null),
                    HttpStatus.OK
            );
        }
        for (Apartment apartment: apartments){
            List<String> listImageUrlOfApartment = imagesOfApartmentService.getListImageUrlOfApartmentByApartmentId(apartment.getId());
            apartment.setImageUrls(listImageUrlOfApartment);
            List<ApartmentOrders> apartmentOrders = apartmentOrdersService.findApartmentOrdersByApartmentId(apartment.getId());
            apartment.setApartmentOrders(apartmentOrders);
        }
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Successfully. Get list all apartments", apartments),
                HttpStatus.OK);
    }
    @RequestMapping(value = "/apartments/{id}", method = RequestMethod.GET)
    public ResponseEntity<StandardResponse> getApartment(@PathVariable Long id){
        Apartment apartment = apartmentService.findById(id);

        if (apartment == null){
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found apartment", null),
                    HttpStatus.OK);
        }
        List<String> listImageUrlOfApartment = imagesOfApartmentService.getListImageUrlOfApartmentByApartmentId(apartment.getId());
        apartment.setImageUrls(listImageUrlOfApartment);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Successfully. Get apartment details", apartment),
                HttpStatus.OK);

    }

}
