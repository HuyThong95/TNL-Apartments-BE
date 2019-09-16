package com.codegym.tnlapartmentsbe.model;

import javax.persistence.*;

@Entity
public class ImagesOfApartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private Apartment apartment;

    public ImagesOfApartment() {
    }

    public ImagesOfApartment(String imageUrl, Apartment apartment) {
        this.imageUrl = imageUrl;
        this.apartment = apartment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }
}
