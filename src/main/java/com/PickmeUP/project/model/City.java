package com.PickmeUP.project.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "CITY")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "city_id", nullable = false, columnDefinition = "INT(5)")
    private int id;

    @Column(name = "city_name", nullable = false, columnDefinition = "VARCHAR(30)")
    private String city_name;

    @Column(name = "creation", nullable = false, columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private Timestamp creation;

    @OneToMany (mappedBy = "city")
    private List<Stop> stops;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCity_name() {
        return this.city_name;
    }

    public void setCreation(Timestamp creation) {
        this.creation = creation;
    }

    public Timestamp getCreation() {
        return this.creation;
    }
}