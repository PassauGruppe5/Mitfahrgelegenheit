package com.PickmeUP.project.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;
    @Column(name = "email")
    @Email(message = "*Bitte geben Sie eine gültige Email an")
    @NotEmpty(message = "*Bitte geben Sie eine Email an")
    private String email;
    @Column(name = "password")
    @Length(min = 8, message = "*Ihr Passwort muss mindestens 8 Zeichen lang sein")
    @NotEmpty(message = "*Bitte geben Sie ein Passwort ein")
    private String password;
    @Column(name = "name")
    @NotEmpty(message = "*Bitte geben Sie Ihren Vornamen an")
    private String name;
    @Column(name = "last_name")
    @NotEmpty(message = "*Bitte geben Sie Ihren Nachnamen an")
    private String lastName;
    @Column(name = "active")
    private int active;
    @Column(name = "phone")
    @NotEmpty(message = "*Bitte geben Sie Ihre Handynummer an")
    private String phone;
    @Column(name = "birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "*Bitte geben Sie Ihren Geburtstag an")
    private Date birth;
    @Column(name = "creation")
    @CreationTimestamp
    private Timestamp creation;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @ManyToOne
    @JoinTable(name = "user_bonus", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "bonus_id"))
    private Bonus bonus;

    public String getPassword() { return this.password; }
    public void setPassword(String encodedPassword) {this.password = encodedPassword; }

    public void setActive(int active) {
        this.active = active;
    }

    public void setRoles(HashSet<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {return this.email; }
    public void setEmail(String email){this.email = email;}

    public String getName() {return this.name; }
    public void setName(String name){this.name=name;}

    public String getLastName() {return this.lastName; }
    public void setLastName(String lastName){this.lastName = lastName;}

    public String getPhone(){return this.phone;}
    public void setPhone(String phone){this.phone = phone;}

    public Date getBirth(){return this.birth;}
    public void setBirth(Date birth){this.birth = birth;}

    public Bonus getBonus(){return this.bonus;}
    public void setBonus(Bonus bonus){this.bonus = bonus;}

    public int getId(){return this.id;}
    }