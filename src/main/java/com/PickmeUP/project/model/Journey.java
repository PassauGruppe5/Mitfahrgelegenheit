package com.PickmeUP.project.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Journey")
public class Journey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "arrival",nullable = false)
    private Date arrival;

    @Column(name = "departure",nullable = false)
    private Date departure;

    @Column(name = "bags",nullable = false)
    private int bags;

    @Column(name = "seats",nullable = false)
    private int seats;

    @Column(name = "priceBag",nullable = false)
    private int priceBag;

    @Column(name = "priceKm",nullable = false)
    private int priceKm;

    @Column(name = "route")
    private String route;

    public Journey(){

    }

    public int getId(){return this.id;}

    public Date getArrival(){return this.arrival;}
    public void setArrival(Date arrival){ this.arrival = arrival;}

    public Date getDeparture(){return this.departure;}
    public void setDeparture(Date departure){ this.departure = departure;}

    public int getBags(){return this.bags;}
    public void setBags(int bags){this.bags = bags;}

    public int getSeats(){return this.seats;}
    public void setSeats(int seats){this.seats = seats;}

    public int getPriceBag(){return this.priceBag;}
    public void setPriceBag(int priceBag){this.priceBag = priceBag;}

    public int getPriceKm(){return this.priceKm;}
    public void setPriceKm(int priceKm){this.priceKm = priceKm;}

    public String route() {
        return route;
    }

    public void setroute(String route) {
        this.route = route;
    }
}
