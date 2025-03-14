package com.projeto.apibiblioteca.entities;

import com.projeto.apibiblioteca.enums.OrderStatus;
import com.projeto.apibiblioteca.enums.OrderType;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Integer version = 0;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "order_books", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> books;

    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Instant orderDate;
    private LocalDate withdrawalDate;
    private LocalDate returnDate;
    private Double totalPrice;

    public Order() {
    }

    public Order(User user, OrderType type, OrderStatus status, Instant orderDate, LocalDate withdrawalDate, LocalDate returnDate) {
        this.user = user;
        this.type = type;
        this.status = status;
        this.orderDate = orderDate;
        this.withdrawalDate = withdrawalDate;
        this.returnDate = returnDate;
    }

    public UUID getId() {
        return id;
    }

    public List<Book> getBooks() {
        return books;
    }

    public User getUser() {
        return user;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDate getWithdrawalDate() {
        return withdrawalDate;
    }

    public void setWithdrawalDate(LocalDate withdrawalDate) {
        this.withdrawalDate = withdrawalDate;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
