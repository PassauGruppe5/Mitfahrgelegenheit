package com.PickmeUP.project.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "CAR")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "car_id", nullable = false, columnDefinition = "INT(10) UNSIGNED ZEROFILL")
    private int id;

    @Column(name = "colour", nullable = false, columnDefinition = "TEXT")
    private String colour;

    @Column(name = "type", nullable = false, columnDefinition = "TEXT")
    private String type;

    @Column(name = "licence", nullable = false, columnDefinition = "CHAR(8)")
    private String licence;

    @Column(name = "creation", nullable = false, columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private Timestamp creation;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getColour() {
        return this.colour;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getLicence() {
        return this.licence;
    }

    public void setCreation(Timestamp creation) {
        this.creation = creation;
    }

    public Timestamp getCreation() {
        return this.creation;
    }
}
