package com.PickmeUP.project.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "PASSENGER")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "INT(10)")
    private int id;

 /*   @ManyToMany
    private Trip trip;*/

    @Column(name = "user_id", nullable = false, columnDefinition = "INT(10) UNSIGNED ZEROFILL")
    private int user_id;

    @Column(name = "active", nullable = false, columnDefinition = "CHAR(1)")
    private String active = "Y";

    @Column(name = "creation", nullable = false, columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private Timestamp creation;

    /*public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }

    public int getTrip_id() {
        return this.trip_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return this.user_id;
    }*/

    public void setCreation(Timestamp creation) {
        this.creation = creation;
    }

    public Timestamp getCreation() {
        return this.creation;
    }
}