package com.PickmeUP.project.model;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "email")
    private String email;

    @Column (name = "phone")
    private String phone;

    @Column (name = "password")
    private String password;

    @Column (name = "birth")
    private String birth;

    @Column (name = "bonus_id")
    private int bonus_id;

    @Column (name = "admin")
    private String admin;

    @Column (name = "active")
    private String active;

    @Column (name = "creation")
    private Timestamp creation;

    public void setLast_name(String last_name){this.last_name = last_name;}
    public String getLast_name(){return this.last_name;}

    public void setId(int id) {this.id = id;}
    public int getId(){return this.id ;}

    public void setFirst_name(String first_name){this.first_name = first_name;}
    public String getFirst_name(){return this.first_name;}

    public void setEmail(String email){this.email = email;}
    public String getEmail(){return this.email;}

    public void setPhone(String phone){this.phone = phone;}
    public String getPhone(){return this.phone;}

    public void setPassword(String password){this.password = password;}
    public String getPassword(){return this.password;}

    public void setBirth(String birth){this.birth = birth;}
    public String getBirth(){return this.birth;}

    public void setBonus_id(int bonus_id){this.bonus_id = bonus_id;}
    public int getBonus_id(){return this.bonus_id;}

    public void setAdmin(String admin){this.admin = admin;}
    public String getAdmin(){return this.admin;}

    public void setActive(String active){this.active = active;}
    public String getActive() { return this.active;}

    public void setCreation(Timestamp creation) {this.creation = creation;}
    public Timestamp getCreation(){return this.creation;}

}
