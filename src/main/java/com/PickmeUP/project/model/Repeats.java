package com.PickmeUP.project.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "REPEATS")
public class Repeats {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "CHAR(1)")
    private String id;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "creation", nullable = false, columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private Timestamp creation;

    @OneToMany (mappedBy = "repeats")
    private List<Trip> trips;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreation() {
        return this.creation;
    }

    public void setCreation(Timestamp creation) {
        this.creation = creation;
    }

}