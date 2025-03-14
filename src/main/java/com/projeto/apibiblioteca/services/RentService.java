package com.projeto.apibiblioteca.services;

import com.projeto.apibiblioteca.entities.Order;
import com.projeto.apibiblioteca.enums.OrderStatus;
import com.projeto.apibiblioteca.enums.OrderType;
import com.projeto.apibiblioteca.exceptions.NotFoundException;
import com.projeto.apibiblioteca.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class RentService {

    @Autowired
    private OrderRepository orderRepository;

    public void processRental(UUID orderId, int rentalDays) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

        if (order.getType() != OrderType.RENT) {
            throw new IllegalArgumentException("Este pedido não é um aluguel.");
        }

        order.setWithdrawalDate(LocalDate.now());
        order.setReturnDate(order.getWithdrawalDate().plusDays(rentalDays));
        order.setTotalPrice(calculateRentalPrice(order, rentalDays));

        order.getBooks().forEach(book -> book.setQuantity(book.getQuantity() - 1));

        orderRepository.save(order);
    }

    private double calculateRentalPrice(Order order, int rentalDays) {
        return order.getBooks().stream()
                .mapToDouble(book -> book.getPrice() * 0.005 * rentalDays)
                .sum();
    }

    public void processReturn(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

        if (order.getType() != OrderType.RENT) {
            throw new IllegalArgumentException("Este pedido não é um aluguel.");
        }

        if (order.getWithdrawalDate() == null || order.getReturnDate() == null) {
            throw new IllegalStateException("Datas de retirada ou devolução não definidas.");
        }

        LocalDate withdrawalDate = order.getWithdrawalDate();
        LocalDate expectedReturnDate = order.getReturnDate();
        LocalDate actualReturnDate = LocalDate.now();

        long rentalDays = ChronoUnit.DAYS.between(withdrawalDate, actualReturnDate);

        double rentalPrice = calculateRentalPrice(order, (int) rentalDays);

        double fine = calculateFine(order, expectedReturnDate, actualReturnDate);

        order.setTotalPrice(rentalPrice + fine);

        order.setReturnDate(actualReturnDate);
        order.setStatus(OrderStatus.RETURNED);

        order.getBooks().forEach(book -> book.setQuantity(book.getQuantity() + 1));

        orderRepository.save(order);
    }


    private double calculateFine(Order order, LocalDate expectedReturnDate, LocalDate actualReturnDate) {
        long daysLate = ChronoUnit.DAYS.between(expectedReturnDate, actualReturnDate);

        if (daysLate <= 0) {
            return 0.0;
        }

        double fineRate = 0.01;

        return order.getBooks().stream()
                .mapToDouble(book -> book.getPrice() * fineRate * daysLate)
                .sum();
    }

}
