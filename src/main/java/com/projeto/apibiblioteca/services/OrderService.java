package com.projeto.apibiblioteca.services;

import com.projeto.apibiblioteca.entities.Book;
import com.projeto.apibiblioteca.entities.Order;
import com.projeto.apibiblioteca.enums.OrderType;
import com.projeto.apibiblioteca.exceptions.NotFoundException;
import com.projeto.apibiblioteca.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void placeOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

        if (order.getType() != OrderType.PURCHASE) {
            throw new IllegalArgumentException("Este pedido não é uma compra.");
        }

        order.getBooks().forEach(book -> book.setQuantity(book.getQuantity() - 1));

        orderRepository.save(order);
    }

    public double calculateTotal(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

        return order.getBooks().stream()
                .mapToDouble(Book::getPrice)
                .sum();
    }
}
