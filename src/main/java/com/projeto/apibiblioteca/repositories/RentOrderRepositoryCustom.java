package com.projeto.apibiblioteca.repositories;

import com.projeto.apibiblioteca.entities.Book;
import com.projeto.apibiblioteca.entities.RentOrder;
import com.projeto.apibiblioteca.entities.User;
import com.projeto.apibiblioteca.enums.OrderStatus;
import com.projeto.apibiblioteca.enums.OrderType;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public interface RentOrderRepositoryCustom {
    List<RentOrder> findByFilters(
            User user,
            Instant orderDate,
            LocalDate withdrawDate,
            LocalDate returnDate,
            OrderStatus orderStatus,
            OrderType orderType,
            Book book
    );
}
