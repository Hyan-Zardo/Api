package com.projeto.apibiblioteca.repositories;

import com.projeto.apibiblioteca.entities.*;
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
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, UUID> {
    @Query("""
        SELECT p FROM PurchaseOrder p
        WHERE (:user    IS NULL OR p.user = :user)
          AND (:orderDate IS NULL OR p.orderDate = :orderDate)
          AND (:status  IS NULL OR p.status = :status)
          AND (:type    IS NULL OR p.orderType = :orderType)
    """)
    List<PurchaseOrder> findByFilters(
            @Param("user")      User user,
            @Param("orderDate") Instant orderDate,
            @Param("status")    OrderStatus status,
            @Param("type")      OrderType orderType
    );

    @Query("SELECT p FROM PurchaseOrder p WHERE :book MEMBER OF r.books")
    List<PurchaseOrder> findByBook(@Param("book") Book book);
}