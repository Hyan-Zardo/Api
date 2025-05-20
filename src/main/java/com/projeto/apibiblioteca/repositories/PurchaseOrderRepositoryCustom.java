package com.projeto.apibiblioteca.repositories;

import com.projeto.apibiblioteca.entities.Book;
import com.projeto.apibiblioteca.entities.PurchaseOrder;
import com.projeto.apibiblioteca.entities.User;
import com.projeto.apibiblioteca.enums.OrderStatus;
import com.projeto.apibiblioteca.enums.OrderType;

import java.time.Instant;
import java.util.List;

public interface PurchaseOrderRepositoryCustom {
    List<PurchaseOrder> findByFilters(User user,
                                      Instant orderDate,
                                      OrderStatus orderStatus,
                                      OrderType orderType,
                                      Book book);
}
