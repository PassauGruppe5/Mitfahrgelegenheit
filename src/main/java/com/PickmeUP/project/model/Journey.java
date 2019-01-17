package com.PickmeUP.project.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Journey")
public class Journey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "arrival", nullable = false)
    private Date arrival;

    @Column(name = "departure", nullable = false)
    private Date departure;

    @Column(name = "bags", nullable = false)
    private int bags;

    @Column(name = "seats", nullable = false)
    private int seats;

    @Column(name = "priceBag", nullable = false)
    private int priceBag;

    @Column(name = "priceKm", nullable = false)
    private int priceKm;

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

    @ManyToOne
    @JoinColumn(name = "driver")
    private User driver;

    @ManyToOne
    private Repeat repeat;

    public Journey() {}
    public int getId() {return this.id;}

    public Date getArrival() {return this.arrival;}
    public void setArrival(Date arrival) { this.arrival = arrival;}

    public Date getDeparture() {return this.departure;}
    public void setDeparture(Date departure) {this.departure = departure;}

    public int getBags() { return this.bags;}
    public void setBags(int bags) {this.bags = bags;}

    public int getSeats() {return this.seats;}
    public void setSeats(int seats) {this.seats = seats;}

    public int getPriceBag() {return this.priceBag;}
    public void setPriceBag(int priceBag) {this.priceBag = priceBag;}

    public int getPriceKm() {return this.priceKm;}
    public void setPriceKm(int priceKm) {this.priceKm = priceKm;}

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

    public Repeat getRepeat() {return this.repeat;}
    public void setRepeat(Repeat repeat) {this.repeat = repeat;}

    public void setCreation(Timestamp creation) {this.creation = creation;}
    public Timestamp getCreation() {return this.creation;}

    public boolean checkDate(Date search) {
        if (search.before(this.getDeparture())) {
            return true;
        }

        if (search.equals(this.getDeparture())){
            return true;
        }
        else {
            return false;
        }

    }
}
