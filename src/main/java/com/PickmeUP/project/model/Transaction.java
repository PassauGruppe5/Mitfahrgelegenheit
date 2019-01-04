package com.PickmeUP.project.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "TRANSACTION")
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

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getCreation() {
        return this.creation;
    }
    public void setCreation(Timestamp creation) {
        this.creation = creation;
    }

    public User getReceiver(){return this.receiver;}
    public void setReceiver(User receiver){this.receiver = receiver;}

    public User getTransmitter(){return this.transmitter;}
    public void setRTransmitter(User transmitter){this.receiver = transmitter;}

    public void setReceiverAndTransmitter(User both){this.receiver = both; this.transmitter=both;}

}