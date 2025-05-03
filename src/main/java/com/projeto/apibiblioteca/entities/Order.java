package com.projeto.apibiblioteca.entities;

import com.projeto.apibiblioteca.enums.OrderStatus;
import com.projeto.apibiblioteca.enums.OrderType;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@MappedSuperclass
public abstract class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(name = "order_books",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> books;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private Instant orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    public Order(User user, List<Book> books) {
        this.user = user;
        this.books = books;

        this.orderDate = Instant.now();

        this.status = OrderStatus.ESPERANDO_PAGAMENTO;
    }

    public Order() {}

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
}
