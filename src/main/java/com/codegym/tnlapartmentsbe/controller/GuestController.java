package com.codegym.tnlapartmentsbe.controller;

import com.codegym.tnlapartmentsbe.form.response.StandardResponse;
import com.codegym.tnlapartmentsbe.model.*;
import com.codegym.tnlapartmentsbe.security.service.UserPrinciple;
import com.codegym.tnlapartmentsbe.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/me")
public class GuestController {
    @Autowired
    private ApartmentService apartmentService;
    @Autowired
    private ApartmentOrdersService apartmentOrdersService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RateService rateService;
    @Autowired
    private UserService userService;

    private UserPrinciple getCurrentUser() {
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('GUEST') or hasRole('ADMIN') or hasRole('HOST')")
    public ResponseEntity<StandardResponse> listOrderOfGuest() {
        List<ApartmentOrders> apartmentOrders = this.apartmentOrdersService.findApartmentOrdersByTenantId(getCurrentUser().getId());

        if (apartmentOrders.isEmpty()) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found data", null),
                    HttpStatus.OK);
        }

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Successfully. Get list orders that was booked by guest", apartmentOrders),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/orders/{id}/order-of-apartment", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('GUEST') or hasRole('ADMIN')")
    public ResponseEntity<StandardResponse> getOrderOfApartment(@PathVariable long id) {
        ApartmentOrders apartmentOrders = this.apartmentOrdersService.findById(id);

        if (apartmentOrders == null) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found data", null),
                    HttpStatus.OK);
        }

        Apartment apartment=apartmentOrders.getApartment();

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Successfully. Get the order of apartment", apartment),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('GUEST') or hasRole('ADMIN')")
    public ResponseEntity<StandardResponse> getDetailOrder(@PathVariable Long id) {
        ApartmentOrders apartmentOrders = this.apartmentOrdersService.findById(id);

        if (apartmentOrders == null) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "Fail. Not found data", null),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Successfully. Get detail order that was booked by guest", apartmentOrders),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/orders/{id}/delete", method = RequestMethod.GET)
    @PreAuthorize("hasRole('GUEST') or hasRole('ADMIN')")
    public ResponseEntity<StandardResponse> deleteOrderApartment(@PathVariable Long id) {
        ApartmentOrders apartmentOrders = this.apartmentOrdersService.findById(id);
        Date checkin = apartmentOrders.getCheckin();
        Date now = new Date();
        int day = 86400 * 1000;
        double nowToCheckinByDay = (double) (checkin.getTime() - now.getTime()) / day;
        if (nowToCheckinByDay < 1.0) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false, "can not cancel order", null),
                    HttpStatus.OK);
        }
        apartmentOrders.setStatusOrder(StatusOrder.CANCELED);
        this.apartmentOrdersService.updateApartmentOrders(apartmentOrders);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "cancelled", null),
                HttpStatus.OK);
    }
    @PostMapping("/comments")
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<StandardResponse> createComment(@RequestBody Comment comment) {
        comment.setUser(this.userService.findById(getCurrentUser().getId()));
        this.commentService.createComment(comment);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "Feedback has been noted", null),
                HttpStatus.CREATED);
    }

    @PostMapping("/rates")
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<StandardResponse> createRate(@RequestBody Rate rate) {
        rate.setUser(this.userService.findById(getCurrentUser().getId()));
        if (this.rateService.existsRateByUserIdAndApartmentId(rate.getUser().getId(), rate.getApartment().getId() ) ){
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(true, "you can rate once", null),
                    HttpStatus.CREATED);
        }
        this.rateService.createRate(rate);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true, "successful", null),
                HttpStatus.CREATED);
    }

    @GetMapping("/rates/{apartmentId}")
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<StandardResponse> getRateByUserIdAndHouseId(@PathVariable Long apartmentId){
        Rate rate = this.rateService.findByUserIdAndApartmentId(getCurrentUser().getId(), apartmentId);
        if(rate == null){
            return new ResponseEntity<StandardResponse>(new StandardResponse(false, "please rate me", null), HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<StandardResponse>(new StandardResponse(true, "successful", rate), HttpStatus.OK);
    }


}
