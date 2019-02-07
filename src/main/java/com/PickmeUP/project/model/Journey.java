package com.PickmeUP.project.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
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
    private double priceBag;

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


    //      gets id of object.
    //
    //      @return id                 the object's id.
    public int getId() {return this.id;}

    //      gets id of object.
    //
    //      @param id                  id to be set.
    //      @return void
    public void setId(int id){this.id = id;}

    //      sets arrivalDate of object.
    //
    //      @return arrivalDate                 the object's arrivalDate.
    public String getArrivalDate() {return this.arrivalDate;}

    //      gets arrivalDate of object.
    //
    //      @param id                  arrivalDate to be set.
    //      @return void
    public void setArrivalDate(String arrivalDate) { this.arrivalDate = arrivalDate;}

    //      sets departureDate of object.
    //
    //      @return departureDate                 the object's departureDate.
    public String getDepartureDate() {return this.departureDate;}

    //      gets departureDate of object.
    //
    //      @param departureDate                  departureDate to be set.
    //      @return void
    public void setDepartureDate(String departureDate) {this.departureDate = departureDate;}

    //      gets bags of object.
    //
    //      @return bags                 the object's bags.
    public int getBags() { return this.bags;}

    //      sets bags of object.
    //
    //      @param bags                  bags to be set.
    //      @return void
    public void setBags(int bags) {this.bags = bags;}

    //      gets seats of object.
    //
    //      @return seats                 the object's seats.
    public int getSeats() {return this.seats;}

    //      sets seats of object.
    //
    //      @param seats                  seats to be set.
    //      @return void
    public void setSeats(int seats) {this.seats = seats;}

    //      gets priceBag of object.
    //
    //      @return priceKm                 the object's priceBag.
    public double getPriceBag() {return this.priceKm;}

    //      sets priceBag of object.
    //
    //      @param priceBag                  priceBag to be set.
    //      @return void
    public void setPriceBag(double priceBag) {this.priceBag = priceBag;}

    //      gets priceKm of object.
    //
    //      @return priceKm                 the object's priceKm.
    public double getPriceKm() {return this.priceKm;}

    //      sets priceKm of object.
    //
    //      @param priceKm                  priceKm to be set.
    //      @return void
    public void setPriceKm(double priceKm) {this.priceKm = priceKm;}

    //      gets route of object.
    //
    //      @return route                 the object's route.
    public String getRoute() {return route;}

    //      sets route of object.
    //
    //      @param route                  route to be set.
    //      @return void
    public void setroute(String route) {this.route = route;}


    //      gets legsInJourney of object.
    //
    //      @return legsInJourney                 the object's legsInJourney.
    public List<Leg> getLegsInJourney() {return this.legsInJourney;}

    //      sets legsInJourney of object.
    //
    //      @param legsInJourney                  legsInJourney to be set.
    //      @return void
    public void setLegsInJourney(List<Leg> legsInJourney) {this.legsInJourney = legsInJourney;}

    //      gets origin of object.
    //
    //      @return origin                 the object's origin.
    public String getOrigin() {return this.origin;}

    //      sets origin of object.
    //
    //      @param origin                  origin to be set.
    //      @return void
    public void setOrigin(String origin) {this.origin = origin;}

    //      gets destination of object.
    //
    //      @return destination                 the object's destination.
    public String getDestination() {return this.destination;}

    //      sets destination of object.
    //
    //      @param destination              destination to be set.
    //      @return void
    public void setDestination(String destination) {this.destination = destination;}

    //      gets driver of object.
    //
    //      @return driver                 the object's driver.
    public User getDriver() {return this.driver;}

    //      sets driver of object.
    //
    //      @param driver                  driver to be set.
    //      @return void
    public void setDriver(User driver) {this.driver = driver;}

    //      gets active of object.
    //
    //      @return active                 the object's active.
    public int getActive() {return active;}

    //      sets active of object.
    //
    //      @param active                 active to be set.
    //      @return void
    public void setActive(int active){this.active = active;}

    //      gets canceled of object.
    //
    //      @return canceled                 the object's canceled.
    public int getCanceled() {return canceled;}

    //      sets calnceled of object.
    //
    //      @param legsInJourney                  calnceled to be set.
    //      @return void
    public void setCanceled(int canceled){this.canceled = canceled;}

    //      gets repeat of object.
    //
    //      @return repeat                 the object's repeat.
    public Repeat getRepeat() {return this.repeat;}

    //      sets repeat of object.
    //
    //      @param repeat                  repeat to be set.
    //      @return void
    public void setRepeat(Repeat repeat) {this.repeat = repeat;}

    //      gets arrivalTime of object.
    //
    //      @return arrivalTime                 the object's arrivalTime.
    public String getArrivalTime(){return this.arrivalTime;}

    //      sets arrivalTime of object.
    //
    //      @param arrivalTime                  arrivalTime to be set.
    //      @return void
    public void setArrivalTime(String arrivalTime){this.arrivalTime=arrivalTime;}

    //      gets dapartureTime of object.
    //
    //      @return dapartureTime                 the object's dapartureTime.
    public String getDepartureTime(){return this.dapartureTime;}

    //      sets dapartureTime of object.
    //
    //      @param dapartureTime                  dapartureTime to be set.
    //      @return void
    public void setDepartureTime(String dapartureTime){this.dapartureTime=dapartureTime;}

    //      gets car of object.
    //
    //      @return car                 the object's car.
    public Car getCar(){return this.car;}

    //      sets car of object.
    //
    //      @param car                  car to be set.
    //      @return void
    public void setCar(Car car){this.car=car;}

    //      sets creation of object.
    //
    //      @param creation                  the creation to be set.
    //      @return void
    public void setCreation(Timestamp creation) {this.creation = creation;}

    //      gets creation of object.
    //
    //      @return creation                 the object's creation.
    public Timestamp getCreation() {return this.creation;}

    //      checks if date departure date equals parameter date. .
    //
    //      @param search                    date to be compared to.
    //      @return boolean                  if it is equal to param.
    public boolean checkDate(Date search) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        if (search.before(formatter.parse(this.getDepartureDate()))) {
//            return true;
//        }

        if (search.equals(formatter.parse(this.getDepartureDate()))){
            return true;
        }
        else {
            return false;
        }

    }

    //      checks if date departure date equals parameter time.
    //
    //      @param searchTime                    date to be compared to.
    //      @return boolean                      if it is after equal or before.
    public boolean checkTime(LocalTime searchTime){
        if(LocalTime.parse(this.getDepartureTime()).isAfter(searchTime)){
            return true;}
        if(LocalTime.parse(this.getDepartureTime()).equals(searchTime)){
            return true;}
        else
            return false;
        }

    //      generated copy of object.
    //
    //      @param tobeCloned                    journey to be cloned.
    //      @return void
     public void clone(Journey toBeCloned){
        this.arrivalDate = toBeCloned.getArrivalDate();
        this.departureDate = toBeCloned.getDepartureDate();
        this.arrivalTime = toBeCloned.getArrivalTime();
        this.bags = toBeCloned.getBags();
        this.canceled = toBeCloned.getCanceled();
        this.dapartureTime = toBeCloned.getDepartureTime();
        this.arrivalDate = toBeCloned.getArrivalDate();
        this.destination = toBeCloned.getDestination();
        this.origin = toBeCloned.getOrigin();
        this.priceBag = toBeCloned.getPriceBag();
        this.priceKm = toBeCloned.getPriceKm();
        this.route = toBeCloned.getRoute();
        this.repeat = toBeCloned.getRepeat();
        this.car = toBeCloned.getCar();
        this.driver = toBeCloned.getDriver();
        this.seats = toBeCloned.getSeats();
        this.legsInJourney = toBeCloned.getLegsInJourney();
        this.active = 1;
     }

    //      checks if journey is a clone.
    //
    //      @param toBeComparedTo                    journey to be compared to.
    //      @return boolean                          wether it is or it is not a clone.
     public boolean checkForClone(Journey toBeComparedTo){
        if(this.driver == toBeComparedTo.getDriver() && this.getDepartureDate() == toBeComparedTo.getDepartureDate() && this.getDepartureTime() == toBeComparedTo.getDepartureTime()){
             return true;
         }

        else{
             return false;
         }
     }
}
