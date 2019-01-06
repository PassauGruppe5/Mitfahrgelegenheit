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

    @Column(name = "grade", columnDefinition = "INT(1)")
    private int grade;

    @Column(name = "creation", nullable = false, columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private Timestamp creation;

    @ManyToOne
    private User publisher;

    @ManyToOne
    private User receiver;

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public int getGrade() {
        return grade;
    }
    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Timestamp getCreation() {
        return this.creation;
    }
    public void setCreation(Timestamp creation) {
        this.creation = creation;
    }

    public User getPublisher(){return this.publisher;}
    public void setPublisher(User publisher){this.publisher=publisher;}

    public User getReceiver() {return receiver;}
    public void setReceiver(User receiver) {this.receiver = receiver; }
}