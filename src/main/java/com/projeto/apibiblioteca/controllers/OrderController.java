package com.projeto.apibiblioteca.controllers;

import com.projeto.apibiblioteca.enums                               .OrderType;
import com.projeto.apibiblioteca.records.OrderRequest;
import com.projeto.apibiblioteca.services.PurchaseService;
import com.projeto.apibiblioteca.services.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private RentService rentService;

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody OrderRequest orderRequest) {
        if (orderRequest.type() == OrderType.ALUGUEL) {
            rentService.processRental(orderRequest);
        } else if (orderRequest.type() == OrderType.COMPRA) {
            purchaseService.processPurchase(orderRequest);
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
