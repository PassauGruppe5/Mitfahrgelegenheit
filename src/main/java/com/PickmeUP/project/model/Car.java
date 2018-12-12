package com.PickmeUP.project.model;

import javax.persistence.*;

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
}
