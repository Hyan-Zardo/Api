package com.projeto.apibiblioteca.entities;


import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Integer version = 0;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    private String description;

    private String category;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String conservation;

    //@Getter
    //@OneToMany(mappedBy = "book", cascade = CascadeType.PERSIST)
    //private List<Assessment> assessments;


    public Book(String title, String author, String description, String category, Double price, Integer quantity, String conservation) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.conservation = conservation;
    }

    public Book() {
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getConservation() {
        return conservation;
    }

    public void setConservation(String conservation) {
        this.conservation = conservation;
    }
}
