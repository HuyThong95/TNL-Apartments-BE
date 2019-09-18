package com.codegym.tnlapartmentsbe.controller;

import com.codegym.tnlapartmentsbe.form.response.StandardResponse;
import com.codegym.tnlapartmentsbe.model.*;
import com.codegym.tnlapartmentsbe.security.service.UserPrinciple;
import com.codegym.tnlapartmentsbe.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/host")
public class HostController {
    @Autowired
    private ApartmentService apartmentService;
    @Autowired
    private UserService userService;
    @Autowired
    private ImagesOfApartmentService imagesOfApartmentService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ApartmentOrdersService apartmentOrdersService;
    @Autowired
    private ApartmentStatusService apartmentStatusService;

    private UserPrinciple getCurrentUser() {
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

    @RequestMapping(method = RequestMethod.GET, value = "/ApartmentStatus/{id}")
    public ResponseEntity<StandardResponse> getApartmentStatusById(@PathVariable Long id) {
        ApartmentStatus apartmentStatus = this.apartmentStatusService.findById(id);

        if (apartmentStatus == null) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found", null),
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Get the status house successfully", apartmentStatus),
                HttpStatus.OK);
    }

    @PutMapping("/ApartmentStatus/{id}")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<StandardResponse> editApartmentStatus(@RequestBody ApartmentStatus apartmentStatus, @PathVariable Long id) {
        ApartmentStatus currentApartmentStatus = this.apartmentStatusService.findById(id);

        if (currentApartmentStatus == null) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found", null),
                    HttpStatus.NOT_FOUND);
        }

        //no update id for StatusHouse
        currentApartmentStatus.setStartDate(apartmentStatus.getStartDate());
        currentApartmentStatus.setEndDate(apartmentStatus.getEndDate());

        this.apartmentStatusService.save(currentApartmentStatus);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Update the status house successfully", null),
                HttpStatus.ACCEPTED);
    }
    @PostMapping("/ApartmentStatus")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<StandardResponse> createApartmentStatus(@RequestBody ApartmentStatus apartmentStatus) {
        this.apartmentStatusService.save(apartmentStatus);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Post a new status house successfully", null),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/ApartmentStatus/{id}")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<StandardResponse> deleteApartmentStatus(@PathVariable Long id) {
        ApartmentStatus apartmentStatus = this.apartmentStatusService.findById(id);

        if (apartmentStatus == null) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found", null),
                    HttpStatus.NOT_FOUND);
        }

        this.apartmentStatusService.deleteById(id);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true,"Delete the status successfully",null),
                HttpStatus.OK);
    }
    @GetMapping("/apartments")
    @PreAuthorize("hasRole('HOST') or hasRole('ADMIN')")
    public ResponseEntity<StandardResponse> listApartmentsOfHost() {
        long userId = getCurrentUser().getId();
        List<Apartment> apartments = apartmentService.findByUserId(userId);
        if (apartments.isEmpty()) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found data", null),
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Successfully. Get list apartments of host", apartments),
                HttpStatus.OK);
    }

    @PostMapping("/apartments")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<StandardResponse> createApartment(@RequestBody List<ImagesOfApartment> imagesOfApartments) {
        //find category
        String typeName = imagesOfApartments.get(0).getApartment().getCategory().getName();
        Category category = categoryService.findByName(typeName);
        //save house
        Apartment apartment = imagesOfApartments.get(0).getApartment();
        apartment.setStatus(Status.AVAILABLE);
        apartment.setCategory(category);
        apartment.setUser(userService.findByUsername(getCurrentUser().getUsername()));
        this.apartmentService.createApartment(apartment);
        //save image of house
        for (ImagesOfApartment imagesOfApartment : imagesOfApartments) {
            imagesOfApartment.setApartment(apartment);
            this.imagesOfApartmentService.createImage(imagesOfApartment);
        }
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Create new apartment successfully", null),
                HttpStatus.CREATED);
    }
    @PutMapping("/apartments/{id}")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<StandardResponse> editApartment(@RequestBody Apartment apartment, @PathVariable Long id) {
        Category category=categoryService.findByName(apartment.getCategory().getName());
        apartment.setCategory(category);

        Apartment currentApartment = this.apartmentService.findById(id);

        if (currentApartment == null) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found", null),
                    HttpStatus.NOT_FOUND);
        }
        currentApartment.setName(apartment.getName());
        currentApartment.setCategory(apartment.getCategory());
        currentApartment.setAddress(apartment.getAddress());
        currentApartment.setNumberOfRooms(apartment.getNumberOfRooms());
        currentApartment.setNumberOfBathRooms(apartment.getNumberOfBathRooms());
        currentApartment.setDescription(apartment.getDescription());
        currentApartment.setPrice(apartment.getPrice());
        currentApartment.setRate(apartment.getRate());
        currentApartment.setArea(apartment.getArea());

        this.apartmentService.updateApartment(currentApartment);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Updated", null),
                HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/apartments/{id}")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<StandardResponse> deleteApartment(@PathVariable Long id) {
        Apartment apartment = this.apartmentService.findById(id);

        if (apartment == null) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found", null),
                    HttpStatus.NOT_FOUND);
        }

        this.apartmentService.deleteApartment(id);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true,"Delete the house successfully",null),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/apartments/orderOfUser/{id}",method = RequestMethod.GET)
    @PreAuthorize("hasRole('HOST')")
    public  ResponseEntity<StandardResponse> getApartmentOrderByUser(@PathVariable("id") Long id){
        List<ApartmentOrders> apartmentOrders = apartmentOrdersService.findApartmentOrdersByApartmentId(id);
        return new ResponseEntity<StandardResponse>(new StandardResponse(true,"list all order",apartmentOrders),HttpStatus.OK);
    }


}
