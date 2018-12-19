package com.PickmeUP.project.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "REPEAT")
public class Repeat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "repeat_id")
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}