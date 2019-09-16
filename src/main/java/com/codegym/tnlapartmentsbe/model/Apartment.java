package com.codegym.tnlapartmentsbe.model;

import javax.persistence.*;

@Entity
@Table( name = "Apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String address;

    private String numberOfRooms;

    private String numberOfBathRooms;

    @Column(columnDefinition = "long")
    private String description;

    private String prize;

    private String area;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private User user;

    public Apartment() {
    }

    public Apartment(String name, String address, String numberOfRooms, String numberOfBathRooms, String description, String prize, String area, User user) {
        this.name = name;
        this.address = address;
        this.numberOfRooms = numberOfRooms;
        this.numberOfBathRooms = numberOfBathRooms;
        this.description = description;
        this.prize = prize;
        this.area = area;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(String numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getNumberOfBathRooms() {
        return numberOfBathRooms;
    }

    public void setNumberOfBathRooms(String numberOfBathRooms) {
        this.numberOfBathRooms = numberOfBathRooms;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
