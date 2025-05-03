package com.projeto.apibiblioteca.entities;

import com.projeto.apibiblioteca.enums.OrderStatus;
import com.projeto.apibiblioteca.enums.OrderType;
import com.projeto.apibiblioteca.exceptions.NotFoundException;
import com.projeto.apibiblioteca.repositories.UserRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "rent_orders")
public class RentOrder extends Order {

    @Column(columnDefinition = "DATE")
    private LocalDate withdrawalDate;

    @Column(columnDefinition = "DATE")
    private LocalDate returnDate;

    @Column(nullable = false)
    private Double totalPrice;

    public RentOrder(User user, List<Book> books, OrderType type, LocalDate withdrawalDate, LocalDate returnDate) {
        super(user, books);
        this.withdrawalDate = withdrawalDate;
        this.returnDate = returnDate;
        this.totalPrice = calculateRentalPrice(books, withdrawalDate, returnDate);
        setOrderType(type);
        setOrderDate(Instant.now());
        setStatus(OrderStatus.ESPERANDO_PAGAMENTO);
    }

    public RentOrder() {}

    public LocalDate getWithdrawalDate() { return withdrawalDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public Double getTotalPrice() { return totalPrice; }

    public void setWithdrawalDate(LocalDate withdrawalDate) {
        this.withdrawalDate = withdrawalDate;
    }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }

    private double calculateRentalPrice(List<Book> books, LocalDate withdrawDate, LocalDate returnDate) {
        Long rentalDays = ChronoUnit.DAYS.between(withdrawDate, returnDate);

        return books.stream()
                .mapToDouble(book -> book.getPrice() * 0.005 * rentalDays)
                .sum();
    }

    private static User findUser(UUID userId, UserRepository userRepository) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
    }
}