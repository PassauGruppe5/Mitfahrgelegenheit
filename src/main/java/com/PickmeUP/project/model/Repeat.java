package com.PickmeUP.project.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "repeats")
public class Repeat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "description")
    private String description;

    public void setDescription(String description) {this.description = description; }
    public String getDescription() { return description; }

}