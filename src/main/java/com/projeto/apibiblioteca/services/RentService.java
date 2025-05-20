package com.projeto.apibiblioteca.services;

import com.projeto.apibiblioteca.entities.Book;
import com.projeto.apibiblioteca.entities.RentOrder;
import com.projeto.apibiblioteca.entities.User;
import com.projeto.apibiblioteca.enums.OrderStatus;
import com.projeto.apibiblioteca.enums.OrderType;
import com.projeto.apibiblioteca.exceptions.NotFoundException;
import com.projeto.apibiblioteca.mappers.OrderMapper;
import com.projeto.apibiblioteca.records.OrderRequest;
import com.projeto.apibiblioteca.records.OrderResponse;
import com.projeto.apibiblioteca.records.UserRecord;
import com.projeto.apibiblioteca.repositories.BookRepository;
import com.projeto.apibiblioteca.repositories.RentOrderRepository;
import com.projeto.apibiblioteca.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class RentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RentOrderRepository orderRepository;

    public RentOrder processRental(OrderRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        List<Book> books = bookRepository.findAllById(request.bookIds());

        if (request.withdrawDate() == null || request.returnDate() == null) {
            throw new IllegalArgumentException("Datas de retirada e devolução são obrigatórias para aluguel");
        }

        RentOrder rentOrder = new RentOrder(user, books, request.withdrawDate(), request.returnDate());
        orderRepository.save(rentOrder);
        return rentOrder;
    }

    public OrderResponse getOrderDetails(UUID id) {
        RentOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));
        return OrderMapper.INSTANCE.toOrderResponse(order);
    }

    public List<OrderResponse> searchOrder(
            User user,
            Instant orderDate,
            LocalDate withdrawDate,
            LocalDate returnDate,
            OrderStatus orderStatus,
            OrderType orderType,
            Book book
    ) {
        List<RentOrder> orders = orderRepository.findByFilters(user, orderDate, withdrawDate, returnDate, orderStatus, orderType, book);

        if (orders.isEmpty()) {
            throw new NotFoundException("Nenhum pedido correspondente.");
        }

        return orders.stream()
                .map(OrderMapper.INSTANCE::toOrderResponse)
                .toList();
    }

    public void deleteOrder(UUID orderId){
        if (orderRepository.existsById(orderId)){
            orderRepository.deleteById(orderId);
        }else {
            throw new NotFoundException("Pedido não encontrado");
        }
    }

    public void updateOrder(OrderResponse orderResponse, UUID orderId){
        RentOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));
        updateData(order, orderResponse);
        orderRepository.save(order);
    }

    public void updateData(RentOrder order, OrderResponse orderResponse){
        order.setStatus(orderResponse.orderStatus());
    }

}
