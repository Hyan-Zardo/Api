package com.projeto.apibiblioteca.services;

import com.projeto.apibiblioteca.entities.Book;
import com.projeto.apibiblioteca.entities.PurchaseOrder;
import com.projeto.apibiblioteca.entities.User;
import com.projeto.apibiblioteca.records.OrderRequest;
import com.projeto.apibiblioteca.repositories.BookRepository;
import com.projeto.apibiblioteca.repositories.PurchaseOrderRepository;
import com.projeto.apibiblioteca.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    public void processPurchase(OrderRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        List<Book> books = bookRepository.findAllById(request.bookIds());

        PurchaseOrder purchaseOrder = new PurchaseOrder(user, books, request.type());
        purchaseOrderRepository.save(purchaseOrder);
    }
}
