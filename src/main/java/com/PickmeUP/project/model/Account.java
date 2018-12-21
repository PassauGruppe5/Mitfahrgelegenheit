package com.PickmeUP.project.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ACCOUNTS")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id", nullable = false, columnDefinition = "INT(10) UNSIGNED ZEROFILL")
    private int id;

    @Column(name = "balance", nullable = false, columnDefinition = "DECIMAL(15,2)")
    private double balance = 0.00;

    @Column(name = "creation", nullable = false, columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private Timestamp creation;

    @OneToOne
    private User user;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setCreation(Timestamp creation) {
        this.creation = creation;
    }

    public Timestamp getCreation() {
        return this.creation;
    }
}