package com.projeto.apibiblioteca.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Integer version = 0;

    @Column(nullable = false)
    private String name;

    private String surname;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    private String password;

    @JsonProperty("isAdmin")
    @Column(nullable = false)
    private boolean isAdmin;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
//    private List<Order> orders;

    public User() {
    }

    public User(String name, String surname, String email, String password, boolean isAdmin) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        System.out.println("Setando isAdmin como: " + admin);
        isAdmin = admin;
    }
}
