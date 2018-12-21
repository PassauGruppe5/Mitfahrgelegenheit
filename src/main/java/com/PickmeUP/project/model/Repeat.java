package com.PickmeUP.project.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "REPEAT")
public class Repeat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "repeat_id", nullable = false, columnDefinition = "CHAR(1)")
    private int id;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "creation", nullable = false, columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private Timestamp creation;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreation(Timestamp creation) {
        this.creation = creation;
    }

    public Timestamp getCreation() {
        return this.creation;
    }
}