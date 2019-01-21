package com.PickmeUP.project.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Journey")
public class Journey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "arrivalDate", nullable = false)
    private String arrivalDate;

    @Column(name = "departureDate", nullable = false)
    private String departureDate;

    @Column(name = "arrivalTime", nullable = false)
    private String arrivalTime;

    @Column(name = "departureTime", nullable = false)
    private String dapartureTime;

    @Column(name = "bags", nullable = false)
    private int bags;

    @Column(name = "seats", nullable = false)
    private int seats;

    @Column(name = "priceBag", nullable = false)
    private int priceBag;

    @Column(name = "priceKm", nullable = false)
    private double priceKm;

    @Column(name = "origin", nullable = false)
    private String origin;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "route", columnDefinition = "LONGTEXT")
    private String route;

    @OneToMany
    private List<Leg> legsInJourney;

    @Column(name = "creation", nullable = false, columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private Timestamp creation;

    @Column(name = "active")
    private int active;

    @Column(name = "canceled")
    private int canceled;

    @ManyToOne
    @JoinColumn(name = "driver")
    private User driver;

    @ManyToOne
    @JoinColumn(name = "car")
    private Car car;

    @ManyToOne
    private Repeat repeat;

    public Journey(){}
    public int getId() {return this.id;}

    public String getArrivalDate() {return this.arrivalDate;}
    public void setArrivalDate(String arrivalDate) { this.arrivalDate = arrivalDate;}

    public String getDepartureDate() {return this.departureDate;}
    public void setDepartureDate(String departureDate) {this.departureDate = departureDate;}

    public int getBags() { return this.bags;}
    public void setBags(int bags) {this.bags = bags;}

    public int getSeats() {return this.seats;}
    public void setSeats(int seats) {this.seats = seats;}

    public int getPriceBag() {return this.priceBag;}
    public void setPriceBag(int priceBag) {this.priceBag = priceBag;}

    public double getPriceKm() {return this.priceKm;}
    public void setPriceKm(double priceKm) {this.priceKm = priceKm;}

    public String getRoute() {return route;}
    public void setroute(String route) {this.route = route;}

    public List<Leg> getLegsInJourney() {return this.legsInJourney;}
    public void setLegsInJourney(List<Leg> legsInJourney) {this.legsInJourney = legsInJourney;}

    public String getOrigin() {return this.origin;}
    public void setOrigin(String origin) {this.origin = origin;}

    public String getDestination() {return this.destination;}
    public void setDestination(String destination) {this.destination = destination;}

    public User getDriver() {return this.driver;}
    public void setDriver(User driver) {this.driver = driver;}

    public int getActive() {return active;}
    public void setActive(int active){this.active = active;}

    public int getCanceled() {return canceled;}
    public void setCanceled(int canceled){this.canceled = canceled;}

    public Repeat getRepeat() {return this.repeat;}
    public void setRepeat(Repeat repeat) {this.repeat = repeat;}

    public String getArrivalTime(){return this.arrivalTime;}
    public void setArrivalTime(String arrivalTime){this.arrivalTime=arrivalTime;}

    public String getDepartureTime(){return this.dapartureTime;}
    public void setDepartureTime(String dapartureTime){this.dapartureTime=dapartureTime;}

    public Car getCar(){return this.car;}
    public void setCar(Car car){this.car=car;}

    public void setCreation(Timestamp creation) {this.creation = creation;}
    public Timestamp getCreation() {return this.creation;}

    public boolean checkDate(Date search, SimpleDateFormat formatter) throws ParseException {

        if (search.before(formatter.parse(this.getDepartureDate()))) {
            return true;
        }

        if (search.equals(formatter.parse(this.getDepartureDate()))){
            return true;
        }
        else {
            return false;
        }

    }
}
