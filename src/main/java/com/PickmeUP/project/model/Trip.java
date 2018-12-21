package com.PickmeUP.project.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "TRIP")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trip_id", nullable = false, columnDefinition = "INT(10)")
    private int id;

    @Column(name = "creation", nullable = false, columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private Timestamp creation;

    @OneToMany (mappedBy = "trip")
    private List<Stop> stops;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setCreation(Timestamp creation) {
        this.creation = creation;
    }

    public Timestamp getCreation() {
        return this.creation;
    }
}