package com.projeto.apibiblioteca.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projeto.apibiblioteca.enums.UserRole;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User implements UserDetails {

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

    public User(String name, String email, String password, UserRole role, String cpf) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.cpf = cpf;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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
