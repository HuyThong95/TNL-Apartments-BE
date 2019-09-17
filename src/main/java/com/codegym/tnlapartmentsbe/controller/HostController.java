package com.codegym.tnlapartmentsbe.controller;

import com.codegym.tnlapartmentsbe.form.response.StandardResponse;
import com.codegym.tnlapartmentsbe.model.ApartmentStatus;
import com.codegym.tnlapartmentsbe.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
}
