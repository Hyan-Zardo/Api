package com.projeto.apibiblioteca.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projeto.apibiblioteca.enums.UserRole;
import jakarta.persistence.*;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    private String cpf;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
//    private List<Order> orders;

    public User() {
    }

    public User(String name, String surname, String email, String password, UserRole role, String cpf) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.cpf = cpf;
    }

    public UUID getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public UserRole getRole() { return role; }

    public void setRole(UserRole role) { this.role = role; }

    public String getCpf() { return cpf; }

    public void setCpf(String cpf) { this.cpf = cpf; }

}
