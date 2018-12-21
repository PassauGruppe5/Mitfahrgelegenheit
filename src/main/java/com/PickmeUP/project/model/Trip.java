package com.PickmeUP.project.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "TRIP")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "INT(10)")
    private int id;

    @Column(name = "start_time", nullable = false, columnDefinition = "DATETIME")
    private Date start_time;

    @Column(name = "arrival_time", nullable = false, columnDefinition = "DATETIME")
    private Date arrival_time;

    @Column(name = "seats", nullable = false, columnDefinition = "INT")
    private int seats;

    @Column(name = "bags", nullable = false, columnDefinition = "INT")
    private int bags;

    @Column(name = "km_price", nullable = false, columnDefinition = "DECIMAL(15,2)")
    private int km_price;

    @Column(name = "bag_price", nullable = false, columnDefinition = "DECIMAL(15,2)")
    private int bag_price;

    @Column(name = "active", nullable = false, columnDefinition = "CHAR(1)")
    private String active = "Y";

    @Column(name = "creation", nullable = false, columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private Timestamp creation;

    @OneToMany(mappedBy = "trip")
    private List<Stop> stops;

    @ManyToOne
    private Car car;

    @ManyToOne
    private Repeat repeat;

    @ManyToOne
    private User driver;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(Date arrival_time) {
        this.arrival_time = arrival_time;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getBags() {
        return bags;
    }

    public void setBags(int bags) {
        this.bags = bags;
    }

    public int getKm_price() {
        return km_price;
    }

    public void setKm_price(int km_price) {
        this.km_price = km_price;
    }

    public int getBag_price() {
        return bag_price;
    }

    public void setBag_price(int bag_price) {
        this.bag_price = bag_price;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Timestamp getCreation() {
        return this.creation;
    }

    public void setCreation(Timestamp creation) {
        this.creation = creation;
    }
}