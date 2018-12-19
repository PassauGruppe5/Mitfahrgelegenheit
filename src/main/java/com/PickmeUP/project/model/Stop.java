package com.PickmeUP.project.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "STOP")
public class Stop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "stop_id")
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
