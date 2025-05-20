package com.projeto.apibiblioteca.controllers;

import com.projeto.apibiblioteca.entities.Book;
import com.projeto.apibiblioteca.entities.PurchaseOrder;
import com.projeto.apibiblioteca.entities.User;
import com.projeto.apibiblioteca.enums.OrderStatus;
import com.projeto.apibiblioteca.enums.OrderType;
import com.projeto.apibiblioteca.mappers.OrderMapper;
import com.projeto.apibiblioteca.records.OrderRequest;
import com.projeto.apibiblioteca.records.OrderResponse;
import com.projeto.apibiblioteca.services.BookService;
import com.projeto.apibiblioteca.services.PurchaseService;
import com.projeto.apibiblioteca.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/purchase_orders")
public class PurchaseController {

    @Autowired
    private PurchaseService service;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<OrderRequest> createOrder(@RequestBody OrderRequest orderRequest) {
        PurchaseOrder purchaseOrder = service.processPurchase(orderRequest);
        OrderResponse orderResponse = OrderMapper.INSTANCE.toOrderResponse(purchaseOrder);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(orderResponse.id()).toUri();
        return ResponseEntity.created(uri).body(orderRequest);
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable("id") UUID id) {
        OrderResponse orderResponse = service.getOrderDetails(id);
        return ResponseEntity.ok().body(orderResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<List<OrderResponse>> searchOrders(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID bookId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant orderDate,
            @RequestParam(required = false) OrderStatus orderStatus,
            @RequestParam(required = false) OrderType orderType
    ) {
        User user = (userId != null) ? userService.findById(userId) : null;
        Book book = (bookId != null) ? bookService.findById(bookId) : null;

        List<OrderResponse> orders = service.searchOrder(
                user, orderDate, orderStatus, orderType, book
        );

        return ResponseEntity.ok(orders);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") UUID id){
        service.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<OrderResponse> updateOrder(@RequestBody OrderResponse orderResponse) {
        PurchaseOrder order = OrderMapper.INSTANCE.toPurchaseOrder(orderResponse);
        service.updateOrder(orderResponse, order.getId());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(orderResponse.id()).toUri();
        return ResponseEntity.created(uri).body(orderResponse);
    }

}
