package com.codegym.tnlapartmentsbe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ApartmentOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "apartment_id")
    @JsonBackReference
    private Apartment apartment;

    @ManyToOne
    @JoinColumn
    private User tenant;

    private Date checkin;

    private Date checkout;

    private Long numberGuest;

    private Long cost;

    private Date orderTime;

    @Enumerated(EnumType.STRING)
    private StatusOrder statusOrder;

    public ApartmentOrders() {
    }

    public ApartmentOrders(Apartment apartment, User tenant, Date checkin, Date checkout, Long numberGuest, Long cost, Date orderTime, StatusOrder statusOrder) {
        this.apartment = apartment;
        this.tenant = tenant;
        this.checkin = checkin;
        this.checkout = checkout;
        this.numberGuest = numberGuest;
        this.cost = cost;
        this.orderTime = orderTime;
        this.statusOrder = statusOrder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public User getTenant() {
        return tenant;
    }

    public void setTenant(User tenant) {
        this.tenant = tenant;
    }

    public Date getCheckin() {
        return checkin;
    }

    public void setCheckin(Date checkin) {
        this.checkin = checkin;
    }

    public Date getCheckout() {
        return checkout;
    }

    public void setCheckout(Date checkout) {
        this.checkout = checkout;
    }

    public Long getNumberGuest() {
        return numberGuest;
    }

    public void setNumberGuest(Long numberGuest) {
        this.numberGuest = numberGuest;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public StatusOrder getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(StatusOrder statusOrder) {
        this.statusOrder = statusOrder;
    }
}
