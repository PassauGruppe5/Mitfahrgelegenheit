package com.PickmeUP.project.model;

import com.sun.javafx.beans.IDProperty;

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

    @Column(name="last_name")
    private String last_name;

    public void setLast_name(String last_name){this.last_name = last_name;}
    public String getLast_name(){return this.last_name;}


    public void setId(int id) {this.id = id;}
    public int getId(){return this.id ;}

    public void setFirst_name(String first_name){this.first_name = first_name;}
    public String getFirst_name(){return this.first_name;}
}
