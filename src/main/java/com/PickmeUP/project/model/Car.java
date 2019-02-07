package com.PickmeUP.project.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "CAR")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "car_id")
    private int id;

    @Column(name = "colour")
    private String colour;

    @Column(name = "type")
    private String type;

    @Column(name = "licence")
    private String licence;

    @Column(name = "creation")
    @CreationTimestamp
    private Timestamp creation;

//    @OneToMany (mappedBy = "car")
//    private List<Trip> trips;

    public Car(){}

    //      sets id of object.
    //
    //      @param id                  id to be set.
    //      @return void
    public void setId(int id) {
        this.id = id;
    }

    //      gets id of object.
    //
    //      @return id                 the object's id.
    public int getId() {
        return this.id;
    }

    //      sets colour of object.
    //
    //      @param colour               colour to be set.
    //      @return void
    public void setColour(String colour) {
        this.colour = colour;
    }

    //      gets colour of object.
    //
    //      @return colour               the object's colour.
    public String getColour() {
        return this.colour;
    }

    //      sets type of object.
    //
    //      @param type                  type to be set.
    //      @return void
    public void setType(String type) {
        this.type = type;
    }

    //      gets type of object.
    //
    //      @return type                 the object's type.
    public String getType() {
        return this.type;
    }

    //      sets licence of object.
    //
    //      @param licence                licence to be set.
    //      @return void
    public void setLicence(String licence) {
        this.licence = licence;
    }

    //      gets licence of object.
    //
    //      @return licence                 the object's licence.
    public String getLicence() {
        return this.licence;
    }

    //      sets creation of object.
    //
    //      @param creation                  the creation to be set.
    //      @return void
    public void setCreation(Timestamp creation) {
        this.creation = creation;
    }

    //      gets creation of object.
    //
    //      @return creation                 the object's creation.
    public Timestamp getCreation() {
        return this.creation;
    }
}
