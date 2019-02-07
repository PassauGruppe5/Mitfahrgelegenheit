package com.PickmeUP.project.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
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
    @NotEmpty(message = "*Bitte geben Sie Ihren Geburtstag an")
    private String birth;
    @Column(name = "comment")
    private String comment;
    @Column(name = "creation")
    @CreationTimestamp
    private Timestamp creation;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @ManyToOne
    @JoinTable(name = "user_bonus", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "bonus_id"))
    private Bonus bonus;

    //      password id of object.
    //
    //      @return password                 the object's password.
    public String getPassword() { return this.password; }

    //      sets password of object.
    //
    //      @param encodedPassword                  encodedPassword to be set.
    //      @return void
    public void setPassword(String encodedPassword) {this.password = encodedPassword; }

    //      sets active of object.
    //
    //      @param active                  active to be set.
    //      @return void
    public void setActive(int active) {
        this.active = active;
    }

    //      sets roles of object.
    //
    //      @param roles                  roles to be set.
    //      @return void
    public void setRoles(HashSet<Role> roles) {
        this.roles = roles;
    }

    //      email roles of object.
    //
    //      @return roles                 the object's roles.
    public Set<Role> getRoles(){return this.roles;}

    //      email id of object.
    //
    //      @return email                 the object's email.
    public String getEmail() {return this.email; }

    //      sets email of object.
    //
    //      @param email                  email to be set.
    //      @return void
    public void setEmail(String email){this.email = email;}

    //      email name of object.
    //
    //      @return name                 the object's name.
    public String getName() {return this.name; }

    //      sets name of object.
    //
    //      @param name                  name to be set.
    //      @return void
    public void setName(String name){this.name=name;}

    //      email lastName of object.
    //
    //      @return lastName                 the object's lastName.
    public String getLastName() {return this.lastName; }

    //      sets lastName of object.
    //
    //      @param lastName                  lastName to be set.
    //      @return void
    public void setLastName(String lastName){this.lastName = lastName;}

    //      email phone of object.
    //
    //      @return phone                 the object's phone.
    public String getPhone(){return this.phone;}

    //      sets phone of object.
    //
    //      @param phone                  phone to be set.
    //      @return void
    public void setPhone(String phone){this.phone = phone;}

    //      email birth of object.
    //
    //      @return birth                 the object's birth.
    public String getBirth(){return this.birth;}

    //      sets birth of object.
    //
    //      @param birth                  birth to be set.
    //      @return void
    public void setBirth(String birth){this.birth = birth;}

    //      email bonus of object.
    //
    //      @return bonus                 the object's bonus.
    public Bonus getBonus(){return this.bonus;}

    //      sets bonus of object.
    //
    //      @param bonus                  bonus to be set.
    //      @return void
    public void setBonus(Bonus bonus){this.bonus = bonus;}

    //      gets id of object.
    //
    //      @return id                 the object's id.
    public int getId(){return this.id;}

    //      gets comment of object.
    //
    //      @return comment                 the object's comment.
    public String getComment(){return this.comment;}

    //      sets comment of object.
    //
    //      @param comment                  comment to be set.
    //      @return void
    public void setComment(String comment){this.comment=comment;}
    }