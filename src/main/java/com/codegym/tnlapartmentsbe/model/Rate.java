package com.codegym.tnlapartmentsbe.model;

import javax.persistence.*;

@Entity
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn
    private User user;

    private Long ratePoint;

    @ManyToOne
    @JoinColumn
    private Apartment apartment;

    public Rate() {
    }

    public Rate(User user, Long ratePoint, Apartment apartment) {
        this.user = user;
        this.ratePoint = ratePoint;
        this.apartment = apartment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getRatePoint() {
        return ratePoint;
    }

    public void setRatePoint(Long ratePoint) {
        this.ratePoint = ratePoint;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }
}
