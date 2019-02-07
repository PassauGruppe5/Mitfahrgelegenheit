package com.PickmeUP.project.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "INT(10) UNSIGNED ZEROFILL")
    private int id;

    @Column(name = "amount", nullable = false, columnDefinition = "DECIMAL(15,2)")
    private double amount;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "creation", nullable = false, columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private Timestamp creation;

    @OneToOne
    private User receiver;

    @OneToOne
    private User transmitter;


    //      gets id of object.
    //
    //      @return id                 the object's id.
    public int getId() {
        return this.id;
    }

    //      sets id of object.
    //
    //      @param id                  id to be set.
    //      @return void
    public void setId(int id) {
        this.id = id;
    }

    //      gets amount of object.
    //
    //      @return amount                 the object's amount.
    public double getAmount() {
        return amount;
    }

    //      sets amount of object.
    //
    //      @param amount                  amount to be set.
    //      @return void
    public void setAmount(double amount) {
        this.amount = amount;
    }

    //      gets message of object.
    //
    //      @return message                 the object's message.
    public String getMessage() {
        return message;
    }

    //      sets message of object.
    //
    //      @param message                  message to be set.
    //      @return void
    public void setMessage(String message) {
        this.message = message;
    }

    //      gets creation of object.
    //
    //      @return creation                 the object's creation.
    public Timestamp getCreation() {
        return this.creation;
    }

    //      sets creation of object.
    //
    //      @param creation                  the creation to be set.
    //      @return void
    public void setCreation(Timestamp creation) {
        this.creation = creation;
    }

    //      gets receiver of object.
    //
    //      @return receiver                 the object's receiver.
    public User getReceiver(){return this.receiver;}

    //      sets receiver of object.
    //
    //      @param receiver                  receiver to be set.
    //      @return void
    public void setReceiver(User receiver){this.receiver = receiver;}

    //      gets transmitter of object.
    //
    //      @return transmitter                 the object's transmitter.
    public User getTransmitter(){return this.transmitter;}

    //      sets transmitter of object.
    //
    //      @param transmitter                  transmitter to be set.
    //      @return void
    public void setTransmitter(User transmitter){this.transmitter = transmitter;}

    //      sets receiver and transmitter for payment-in and payment-out purposes.
    //
    //      @param both                  user that is reffered to.
    //      @return void
    public void setReceiverAndTransmitter(User both){this.receiver = both; this.transmitter=both;}

}