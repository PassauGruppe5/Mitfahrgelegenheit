package com.PickmeUP.project.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bonus")
public class Bonus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bonus_id")
    private int id;

    @Column(name = "description")
    private String description;

    //      sets id of object.
    //
    //      @param id                  id to be set.
    //      @return void
    public void setId(int id) {this.id = id;}

    //      gets id of object.
    //
    //      @return id                 the object's id.
    public int getId() { return id; }

    //      sets description of object.
    //
    //      @param                     the description to be set.
    //      @return void
    public void setDescription(String description) {this.description = description; }

    //      gets description of object.
    //
    //      @return description         the object's description.
    public String getDescription() { return description; }
}
