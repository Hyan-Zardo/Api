package com.projeto.apibiblioteca.repositories;

import com.projeto.apibiblioteca.entities.Book;
import com.projeto.apibiblioteca.entities.RentOrder;
import com.projeto.apibiblioteca.entities.User;
import com.projeto.apibiblioteca.enums.OrderStatus;
import com.projeto.apibiblioteca.enums.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface RentOrderRepository extends JpaRepository<RentOrder, UUID> {
    @Query("""
        SELECT r FROM RentOrder r
        WHERE (:user IS NULL OR r.user = :user)
          AND (:orderDate IS NULL OR r.orderDate = :orderDate)
          AND (:status IS NULL OR r.status = :status)
          AND (:type IS NULL OR r.orderType = :orderType)
    """)
    List<RentOrder> findByFilters(
            @Param("user") User user,
            @Param("orderDate") Instant orderDate,
            @Param("status") OrderStatus status,
            @Param("type") OrderType orderType
    );

    @Query("SELECT r FROM RentOrder r WHERE :book MEMBER OF r.books")
    List<RentOrder> findByBook(@Param("book") Book book);

}
