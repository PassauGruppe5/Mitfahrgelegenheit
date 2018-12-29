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

    public void setId(int id) {this.id = id;}
    public int getId() { return id; }

    public void setDescription(String description) {this.description = description; }
    public String getDescription() { return description; }
}
