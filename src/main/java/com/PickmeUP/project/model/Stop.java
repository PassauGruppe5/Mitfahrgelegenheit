package com.PickmeUP.project.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "STOP")
public class Stop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "stop_id", nullable = false, columnDefinition = "INT(2)")
    private int id;

    @Column(name = "arrival_time", nullable = false, columnDefinition = "DATETIME")
    private Date arrival_time;

    @Column(name = "distance", nullable = false, columnDefinition = "INT")
    private int distance;

    @Column(name = "stop_position", nullable = false, columnDefinition = "INT")
    private Date stop_position;

    @Column(name = "creation", nullable = false, columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private Timestamp creation;

    @ManyToOne
    private Trip trip;

    @ManyToOne
    private City city;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(Date arrival_time) {
        this.arrival_time = arrival_time;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Date getStop_position() {
        return stop_position;
    }

    public void setStop_position(Date stop_position) {
        this.stop_position = stop_position;
    }

    public Timestamp getCreation() {
        return this.creation;
    }

    public void setCreation(Timestamp creation) {
        this.creation = creation;
    }
}
