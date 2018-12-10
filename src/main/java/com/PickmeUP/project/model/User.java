package com.PickmeUP.project.model;

import javax.persistence.*;

@Entity
@Table(name="USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private int id;

    @Column(name="first_name")
    private String first_name;

    public void setId(int id) {this.id = id;}
    public int getId(){return this.id ;}

    public void setFirst_name(String first_name){this.first_name = first_name;}
    public String getFirst_name(){return this.first_name;}
}
