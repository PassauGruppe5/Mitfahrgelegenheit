package com.PickmeUP.project.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "RATING")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "INT(10) UNSIGNED ZEROFILL")
    private int id;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "grade")
    private String grade;

    @Column(name = "creation", nullable = false, columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private Timestamp creation;

    @ManyToOne
    private User publisher;

    @ManyToOne
    private User receiver;


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

    //      gets grade of object.
    //
    //      @return grade                 the object's grade.
    public String getGrade() {
        return grade;
    }

    //      sets grade of object.
    //
    //      @param grade                  grade to be set.
    //      @return void
    public void setGrade(String grade) {
        this.grade = grade;
    }

    //      gets creation of object.
    //
    //      @return creation                 the object's creation.
    public Timestamp getCreation() {
        return this.creation;
    }

    //      sets creation of object.
    //
    //      @param creation                  creation to be set.
    //      @return void
    public void setCreation(Timestamp creation) {
        this.creation = creation;
    }

    //      gets publisher of object.
    //
    //      @return publisher                 the object's publisher.
    public User getPublisher(){return this.publisher;}

    //      sets publisher of object.
    //
    //      @param publisher                  publisher to be set.
    //      @return void
    public void setPublisher(User publisher){this.publisher=publisher;}

    //      gets receiver of object.
    //
    //      @return receiver                 the object's receiver.
    public User getReceiver() {return receiver;}

    //      sets receiver of object.
    //
    //      @param receiver                  receiver to be set.
    //      @return void
    public void setReceiver(User receiver) {this.receiver = receiver; }
}