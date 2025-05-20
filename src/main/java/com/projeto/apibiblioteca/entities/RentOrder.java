package com.projeto.apibiblioteca.entities;

import com.projeto.apibiblioteca.enums.OrderStatus;
import com.projeto.apibiblioteca.enums.OrderType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
//@Table(name = "rent_orders")
public class RentOrder extends Order {

    @Column(columnDefinition = "DATE")
    private LocalDate withdrawDate;

    @Column(columnDefinition = "DATE")
    private LocalDate returnDate;

    @Column(nullable = false)
    private Double totalPrice;

    public RentOrder(User user, List<Book> books, LocalDate withdrawDate, LocalDate returnDate) {
        super(user, books);
        this.withdrawDate = withdrawDate;
        this.returnDate = returnDate;
        this.totalPrice = calculateRentalPrice(books, withdrawDate, returnDate);
        setOrderType(OrderType.ALUGUEL);
        setOrderDate(Instant.now());
        setStatus(OrderStatus.ESPERANDO_PAGAMENTO);
    }

    public RentOrder() {}

    public LocalDate getWithdrawDate() { return withdrawDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public Double getTotalPrice() { return totalPrice; }

    public void setWithdrawDate(LocalDate withdrawalDate) {
        this.withdrawDate = withdrawDate;
    }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }

    private double calculateRentalPrice(List<Book> books, LocalDate withdrawDate, LocalDate returnDate) {
        Long rentalDays = ChronoUnit.DAYS.between(withdrawDate, returnDate);

        return books.stream()
                .mapToDouble(book -> (book.getPrice() * 0.05) * rentalDays)
                .sum();
    }
}