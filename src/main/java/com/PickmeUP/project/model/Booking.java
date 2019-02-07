package com.PickmeUP.project.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "INT(10) UNSIGNED ZEROFILL")
    private int id;

    @Column(name = "amount", nullable = false, columnDefinition = "DECIMAL(15,2)")
    private double amount;

    @Column(name = "bags")
    private int bags;

    @Column(name = "creation", nullable = false, columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private Timestamp creation;

    @Column(name = "active")
    private int active;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private User passenger;

    @ManyToOne
    @JoinColumn(name = "journey_id")
    private Journey journey;

    @OneToOne
    @JoinColumn(name = "bonus_id")
    private Bonus bonus;

    //      gets id of object.
    //
    //      @return id                 the object's id.
    public int getId() {
        return id;
    }

    //      sets id of object.
    //
    //      @param id                  id to be set.
    //      @return void
    public void setId(int id) {
        this.id = id;
    }

    //      sets amount of object.
    //
    //      @param amount               amount to be set.
    //      @return void
    public void setAmount(double amount) {
        this.amount = amount;
    }

    //      gets amount of object.
    //
    //      @return amount              the object's amount.
    public double getAmount() {
        return amount;
    }

    //      gets bonus of object.
    //
    //      @return bonus               the object's bonus.
    public Bonus getBonus() {
        return bonus;
    }

    //      sets bonus of object.
    //
    //      @param bonus               bonus to be set.
    //      @return void
    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }

    //      gets bags of object.
    //
    //      @return bags                 the object's bags.
    public int getBags() {
        return bags;
    }

    //      sets bags of object.
    //
    //      @param bags                 bags to be set.
    //      @return void
    public void setBags(int bags) {
        this.bags = bags;
    }

    //      gets journey of object.
    //
    //      @return journey              the object's journey.
    public Journey getJourney() {
        return journey;
    }

    //      sets journey of object.
    //
    //      @param journey               journey to be set.
    //      @return void
    public void setJourney(Journey journey) {
        this.journey = journey;
    }

    //      gets creation of object.
    //
    //      @return creation                 the object's creation.
    public Timestamp getCreation() {
        return creation;
    }

    //      sets creation of object.
    //
    //      @param creation                the creation to be set.
    //      @return void
    public void setCreation(Timestamp creation) {
        this.creation = creation;
    }

    //      gets passenger of object.
    //
    //      @return passenger                 the object's passenger.
    public User getPassenger() {
        return passenger;
    }

    //      sets passenger of object.
    //
    //      @param passenger               passenger to be set.
    //      @return void
    public void setPassenger(User passenger) {
        this.passenger = passenger;
    }

    //      gets active of object.
    //
    //      @return active                 the object's active.
    public int getActive() {
        return active;
    }

    //      sets active of object.
    //
    //      @param active                 active value to be set.
    //      @return void
    public void setActive(int active) {
        this.active = active;
    }

    //      default constructor.
    public Booking(){}

    //      constructor with user element to set values on creation.
    //
    //      @param user                    the user to be used for the constructor.
    public Booking(User user){
        this.bonus = user.getBonus();

        this.passenger = user;
    }
}