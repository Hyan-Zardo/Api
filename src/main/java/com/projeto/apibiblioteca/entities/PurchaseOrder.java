package com.projeto.apibiblioteca.entities;

import com.projeto.apibiblioteca.enums.OrderStatus;
import com.projeto.apibiblioteca.enums.OrderType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.List;

@Entity
//@Table(name = "purchase_orders")
public class PurchaseOrder extends Order {

    @Column (nullable = false)
    private Double totalPrice;

    public PurchaseOrder(User user, List<Book> books) {
        super(user, books);
        this.totalPrice = books.stream().mapToDouble(Book::getPrice).sum();
        setOrderType(OrderType.COMPRA);
        setOrderDate(Instant.now());
        setStatus(OrderStatus.ESPERANDO_PAGAMENTO);
    }

    public PurchaseOrder() {}

    public Double getTotalPrice() { return totalPrice; }
}
