package com.PickmeUP.project.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
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
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    public String getPassword() {
        return this.password;
    }

    public void setPassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public void setRoles(HashSet<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {return this.email; }

    public String getName() {return this.name; }

    public String getLastName() {return this.lastName; }

    public String getPhone(){return this.phone;}
    public void setPhone(String phone){this.phone = phone;}

    public String getBirth(){return this.birth;}
    public void setBirth(String birth){this.birth = birth;}
}