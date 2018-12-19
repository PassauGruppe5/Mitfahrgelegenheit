package com.PickmeUP.project.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "CAR")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "car_id")
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    @Column(name = "colour")
    private String colour;

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getColour() {
        return this.colour;
    }

    @Column(name = "type")
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    @Column(name = "licence")
    //CHAR (8) muss noch implementiert werden
    private String licence;

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getLicence() {
        return this.licence;
    }

    @Column(name = "creation")
    private Timestamp creation;

    public void setCreation(Timestamp creation) {
        this.creation = creation;
    }

    public Timestamp getCreation() {
        return this.creation;
    }
}
