package com.PickmeUP.project.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "INT(10) UNSIGNED ZEROFILL")
    private int id;

    @Column(name = "first_name", nullable = false, columnDefinition = "VARCHAR(30) ")
    private String first_name;

    @Column(name = "last_name", nullable = false, columnDefinition = "VARCHAR(30)")
    private String last_name;

    @Column(name = "email", nullable = false, columnDefinition = "VARCHAR(255)")
    private String email;

    @Column(name = "phone", nullable = false, columnDefinition = "VARCHAR(15)")
    private String phone;

    @Column(name = "password", nullable = false, columnDefinition = "CHAR(60)")
    private String password;

    @Column(name = "birth", columnDefinition = "DATE")
    private String birth;

    @Column(name = "admin", nullable = false, columnDefinition = "CHAR(1)")
    private String admin = "N";

    @Column(name = "active", nullable = false, columnDefinition = "CHAR(1)")
    private String active = "Y";

    @Column(name = "creation", nullable = false, columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private Timestamp creation;

    @OneToMany (mappedBy = "driver")
    private List<Trip> trips;

    @OneToMany (mappedBy = "publisher")
    private List<Rating> published_ratings;

    @OneToMany (mappedBy = "receiver")
    private List<Rating> received_ratings;

    @ManyToOne
    private Bonus bonus;

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLast_name() {
        return this.last_name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getFirst_name() {
        return this.first_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getBirth() {
        return this.birth;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getAdmin() {
        return this.admin;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getActive() {
        return this.active;
    }

    public void setCreation(Timestamp creation) {
        this.creation = creation;
    }

    public Timestamp getCreation() {
        return this.creation;
    }

}

