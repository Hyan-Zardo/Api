package com.projeto.apibiblioteca.services;

import com.projeto.apibiblioteca.entities.Book;
import com.projeto.apibiblioteca.entities.PurchaseOrder;
import com.projeto.apibiblioteca.entities.RentOrder;
import com.projeto.apibiblioteca.entities.User;
import com.projeto.apibiblioteca.enums.OrderStatus;
import com.projeto.apibiblioteca.enums.OrderType;
import com.projeto.apibiblioteca.exceptions.NotFoundException;
import com.projeto.apibiblioteca.mappers.OrderMapper;
import com.projeto.apibiblioteca.records.OrderRequest;
import com.projeto.apibiblioteca.records.OrderResponse;
import com.projeto.apibiblioteca.repositories.BookRepository;
import com.projeto.apibiblioteca.repositories.PurchaseOrderRepository;
import com.projeto.apibiblioteca.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class PurchaseService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PurchaseOrderRepository orderRepository;

    public PurchaseOrder processPurchase(OrderRequest request) {

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Book book = bookRepository.findById(request.bookIds().get(0))
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));

        book.setQuantity(book.getQuantity() - request.quantity());
        bookRepository.save(book);

        List<Book> books = Collections.nCopies(request.quantity(), book);

        PurchaseOrder purchaseOrder = new PurchaseOrder(user, books);
        purchaseOrder.setQuantity(request.quantity());

        return orderRepository.save(purchaseOrder);
    }
    public OrderResponse getOrderDetails(UUID id){
       PurchaseOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));
        return OrderMapper.INSTANCE.toOrderResponse(order);
    }

    public List<OrderResponse> searchOrder(
            User user,
            Instant orderDate,
            OrderStatus orderStatus,
            OrderType orderType,
            Book book,
            String userName,
            String bookTitle
    ) {
        List<PurchaseOrder> orders = orderRepository.findByFilters(user, orderDate, orderStatus, orderType, book, userName, bookTitle);

        if (orders.isEmpty()) {
            throw new NotFoundException("Nenhum pedido correspondente.");
        }

        return orders.stream()
                .map(OrderMapper.INSTANCE::toOrderResponse)
                .toList();
    }

    public void deleteOrder(UUID orderId){
        if (orderRepository.existsById(orderId)){
            PurchaseOrder order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

            Book book = bookRepository.findById(order.getBooks().getFirst().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));

            book.setQuantity(book.getQuantity() + order.getQuantity());
            bookRepository.save(book);

            orderRepository.deleteById(orderId);
        }else {
            throw new NotFoundException("Pedido não encontrado");
        }
    }

    public void updateOrder(OrderResponse orderResponse, UUID orderId){
        PurchaseOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));
        updateData(order, orderResponse);
        orderRepository.save(order);
    }

    public void updateData(PurchaseOrder order, OrderResponse orderResponse){
        order.setStatus(orderResponse.orderStatus());
    }
}
