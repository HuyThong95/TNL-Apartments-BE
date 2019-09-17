package com.codegym.tnlapartmentsbe.controller;

import com.codegym.tnlapartmentsbe.form.response.StandardResponse;
import com.codegym.tnlapartmentsbe.model.*;
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
    @RequestMapping(value = "/apartments/{id}/booking", method = RequestMethod.POST)
    public ResponseEntity<StandardResponse> bookingApartment(@PathVariable Long id, @RequestBody ApartmentOrders apartmentOrders) {
        boolean isBooked =
                apartmentOrdersService.existsApartmentOrdersByCheckinGreaterThanEqualAndCheckinLessThanEqualAndApartmentId(
                        apartmentOrders.getCheckin(), apartmentOrders.getCheckout(), id)
                        || apartmentOrdersService.existsApartmentOrdersByCheckoutGreaterThanEqualAndCheckoutLessThanEqualAndApartmentId(
                        apartmentOrders.getCheckin(), apartmentOrders.getCheckout(), id)
                        || apartmentOrdersService.existsApartmentOrdersByCheckinGreaterThanEqualAndCheckoutLessThanEqualAndApartmentId(
                        apartmentOrders.getCheckin(), apartmentOrders.getCheckout(), id)
                        || apartmentOrdersService.existsApartmentOrdersByCheckinLessThanEqualAndCheckoutGreaterThanEqualAndApartmentId(
                        apartmentOrders.getCheckin(), apartmentOrders.getCheckout(), id)
                        || apartmentOrdersService.existsApartmentStatusByEndDateGreaterThanEqualAndEndDateLessThanEqual(apartmentOrders.getCheckin(), apartmentOrders.getCheckout(), id)
                        || apartmentOrdersService.existsApartmentStatusByStartDateGreaterThanEqualAndEndDateLessThanEqual(apartmentOrders.getCheckin(), apartmentOrders.getCheckout(), id)
                        || apartmentOrdersService.existsApartmentStatusByStartDateGreaterThanEqualAndStartDateLessThanEqual(apartmentOrders.getCheckin(), apartmentOrders.getCheckout(), id)
                        || apartmentOrdersService.existsApartmentStatusByStartDateLessThanEqualAndEndDateGreaterThanEqual(apartmentOrders.getCheckin(), apartmentOrders.getCheckout(), id);
        if (isBooked) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Ngày này nhà đã được đặt. Bạn vui lòng đặt vào ngày khác", null),
                    HttpStatus.OK);
        }
        Apartment apartment = apartmentService.findById(id);
        apartmentOrders.setApartment(apartment);
        User tenant = userService.findById(getCurrentUser().getId());
        apartmentOrders.setTenant(tenant);
        apartmentOrders.setStatusOrder(StatusOrder.PROCESSING);
        apartmentOrdersService.createApartmentOrders(apartmentOrders);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Đặt nhà thành công", null),
                HttpStatus.CREATED);
    }

    @RequestMapping(value = "/apartment/all-user-order", method = RequestMethod.GET)
    public ResponseEntity<StandardResponse> allUserOder() {
        List<ApartmentOrders> apartmentOrders = apartmentOrdersService.findAll();
        return new ResponseEntity<StandardResponse>(new StandardResponse(true, "list all order", apartmentOrders), HttpStatus.OK);
    }

    @RequestMapping(value = "/statusApartment/{apartmentId}", method = RequestMethod.GET)
    private ResponseEntity<StandardResponse> listStatusHouse(@PathVariable Long apartmentId) {
        List<ApartmentStatus> apartmentStatuses = this.apartmentStatusService.findAllByApartmentId(apartmentId);

        if (apartmentStatuses.isEmpty()) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(true, "Successfully but not found data", null),
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Successfully. Get list status houses", apartmentStatuses),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/comments/{apartmentId}", method = RequestMethod.GET)
    public ResponseEntity<StandardResponse> listCommentsByHouseId(@PathVariable Long apartmentId) {
        List<Comment> comments = this.commentService.findAllByApartmentId(apartmentId);

        if (comments.isEmpty()) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found data", null),
                    HttpStatus.OK);
        }

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Successfully. Get list comments that was booked by guest", comments),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/rates/{apartmentId}", method = RequestMethod.GET)
    public ResponseEntity<StandardResponse> listRatesByHouseId(@PathVariable Long apartmentId) {
        List<Rate> rates = this.rateService.findAllByApartmentId(apartmentId);

        if (rates.isEmpty()) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found data", null),
                    HttpStatus.OK);
        }

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Successfully. Get list comment that was booked by guest", rates),
                HttpStatus.OK);
    }






}
