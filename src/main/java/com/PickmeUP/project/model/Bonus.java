package com.PickmeUP.project.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "BONUS")
public class Bonus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bonus_id")
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    @Column(name = "description")
    private String description;

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    @Column(name = "creation")
    private Timestamp creation;

    public void setCreation(Timestamp creation) {
        this.creation = creation;
    }

    public Timestamp getCreation() {
        return this.creation;
    }
}