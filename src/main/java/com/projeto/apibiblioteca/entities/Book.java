package com.projeto.apibiblioteca.entities;

import com.projeto.apibiblioteca.enums.BookConservation;
import jakarta.persistence.*;

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

    @Column(nullable = false)
    private String isbn;

    @Column(length = 2000)
    private String description;

    private String category;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookConservation conservation;

    @Column(nullable = false)
    private String imageUrl;

    private String identify;

    //@Getter
    //@OneToMany(mappedBy = "book", cascade = CascadeType.PERSIST)
    //private List<Assessment> assessments;


    public Book(String title, String author, String isbn, String description, String category, Double price, Integer quantity, BookConservation conservation, String imageUrl, String identify) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.conservation = conservation;
        this.imageUrl = imageUrl;
        this.identify = identify;
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

    public String getIsbn() {return isbn;}

    public void setIsbn(String isbn) {this.isbn = isbn;}

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

    public BookConservation getConservation() {
        return conservation;
    }

    public void setConservation(BookConservation conservation) {
        this.conservation = conservation;
    }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getIdentify() { return identify; }
    public void setIdentify(String identify) { this.identify = identify; }
}
