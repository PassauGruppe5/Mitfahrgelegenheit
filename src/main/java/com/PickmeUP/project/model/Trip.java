package com.PickmeUP.project.model;

import javax.persistence.*;

@Entity
@Table(name = "TRIP")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "distance") //In Metern
    private int distance;

    @Column(name = "duration") //In Sekunden
    private int duration;

    @Column(name = "start_location_lat")
    private double start_location_lat;

    @Column(name = "start_location_lng")
    private double start_location_lng;

    @Column(name = "end_location_lat")
    private double end_location_lat;

    @Column(name = "end_location_lng")
    private double end_location_lng;

    @Column(name = "leg")
    private String leg;

    public int getId() {return this.id; }
    public void setId(int id) {this.id = id;}

    public int getDuration(){return this.duration;}
    public void setDuration(int duration){this.duration = duration;}

    public int getDistance(){return this.distance;}
    public void setDistance(int distance){this.distance = distance;}

    public double getEnd_location_lat() {
        return end_location_lat;
    }

    public double getEnd_location_lng() {
        return end_location_lng;
    }

    public double getStart_location_lat() {
        return start_location_lat;
    }

    public double getStart_location_lng() {
        return start_location_lng;
    }

    public void setEnd_location_lat(double end_location_lat) {
        this.end_location_lat = end_location_lat;
    }

    public void setEnd_location_lng(double end_location_lng) {
        this.end_location_lng = end_location_lng;
    }

    public void setStart_location_lat(double start_location_lat) {
        this.start_location_lat = start_location_lat;
    }

    public void setStart_location_lng(double start_location_lng) {
        this.start_location_lng = start_location_lng;
    }

    public String getLeg() {
        return leg;
    }

    public void setLeg(String leg) {
        this.leg = leg;
    }
}