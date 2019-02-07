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

    //default constructor.
    public Repeat(){}

    //      sets description of object.
    //
    //      @param description                  description to be set.
    //      @return void
    public void setDescription(String description) {this.description = description; }

    //      gets description of object.
    //
    //      @return description                 the object's description.
    public String getDescription() { return description; }

    //      gets id of object.
    //
    //      @return id                 the object's id.
    public int getId(){return this.id;}

}