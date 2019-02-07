package com.PickmeUP.project.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ACCOUNTS")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "INT(10) UNSIGNED ZEROFILL")
    private int id;

    @Column(name = "balance", nullable = false, columnDefinition = "DECIMAL(15,2)")
    private double balance ;

    @Column(name = "creation", nullable = false, columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private Timestamp creation;

    @OneToOne
    private User user;

    //      sets id of object.
    //
    //      @param id                  id to be set.
    //      @return void
    public void setId(int id) {
        this.id = id;
    }

    //      gets id of object.
    //
    //      @return id                 the object's id.
    public int getId() {
        return this.id;
    }

    //      gets user of object.
    //
    //      @return user                 the User.
    public User getUser() {return user;}

    //      sets user of object.
    //
    //      @param user                  the user to be set.
    //      @return void
    public void setUser(User user) {this.user = user;}

    //      sets balance of object.
    //
    //      @param balance                  the balance to be set.
    //      @return void
    public void setBalance(double balance) {
        this.balance = balance;
    }

    //      gets balance of object.
    //
    //      @return balance                 the balance of the object.
    public double getBalance() {
        return this.balance;
    }

    //      sets creation of object.
    //
    //      @param creation                  the creation to be set.
    //      @return void
    public void setCreation(Timestamp creation) {
        this.creation = creation;
    }

    //      gets creation of object.
    //
    //      @return creation                 the creation of the object.
    public Timestamp getCreation() {
        return this.creation;
    }

    //      adds balance to balacne of the object.
    //
    //      @param  plus                 the amout to be added to balance.
    //      @return void
    public void plusBalance(Double plus){
        this.balance= this.balance + plus;
    }

    //      substracts balance to balacne of the object.
    //
    //      @param  minus                 the amout to be substracted of balance.
    //      @return void
    public void minusBalance(Double minus){
        this.balance = this.balance - minus;
    }
}