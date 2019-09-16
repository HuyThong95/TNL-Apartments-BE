package com.codegym.tnlapartmentsbe.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ApartmentStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private  Apartment apartment;

    private Date startDate;

    private Date endDate;

    public ApartmentStatus() {
    }

    public ApartmentStatus(Apartment apartment, Date startDate, Date endDate) {
        this.apartment = apartment;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
