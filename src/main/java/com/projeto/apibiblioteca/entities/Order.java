package com.projeto.apibiblioteca.entities;

import com.projeto.apibiblioteca.enums.OrderStatus;
import com.projeto.apibiblioteca.enums.OrderType;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "orders")
public abstract class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "order_books",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> books;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_date")
    private Instant orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    private String userName;

    public Order(User user, List<Book> books) {
        this.user = user;
        this.books = books;
        this.orderDate = Instant.now();
        this.status = OrderStatus.ESPERANDO_PAGAMENTO;
        this.userName = user.getName();
    }

    public Order() {}

    @PrePersist
    public void prePersist() {
        if (this.orderDate == null) {
            this.orderDate = Instant.now();
        }
    }

    public UUID getId() { return id; }
    public User getUser() { return user; }
    public List<Book> getBooks() { return books; }
    public Instant getOrderDate() { return orderDate; }
    public OrderStatus getStatus() { return status; }
    public OrderType getType() { return orderType; }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public void setOrderDate(Instant orderDate) { this.orderDate = orderDate; }
    public void setOrderType(OrderType type) { this.orderType = type; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
}
