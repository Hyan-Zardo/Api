package com.projeto.apibiblioteca.repositories;

import com.projeto.apibiblioteca.entities.Book;
import com.projeto.apibiblioteca.entities.PurchaseOrder;
import com.projeto.apibiblioteca.entities.User;
import com.projeto.apibiblioteca.enums.OrderStatus;
import com.projeto.apibiblioteca.enums.OrderType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PurchaseOrderRepositoryImpl implements PurchaseOrderRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PurchaseOrder> findByFilters(User user, Instant orderDate, OrderStatus orderStatus, OrderType orderType, Book book, String userName, String bookTitle) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PurchaseOrder> query = cb.createQuery(PurchaseOrder.class);
        Root<PurchaseOrder> root = query.from(PurchaseOrder.class);
        root.fetch("books", JoinType.LEFT);
        List<Predicate> predicates = new ArrayList<>();

        if (user != null) {
            predicates.add(cb.equal(root.get("user"), user));
        }

        if (userName != null && !userName.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("userName")),
                    "%" + userName.toLowerCase() + "%"));
        }

        if (bookTitle != null && !bookTitle.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("bookTitle")),
                    "%" + bookTitle.toLowerCase() + "%"));
        }

        if (orderDate != null) {
            LocalDate localDate = LocalDate.ofInstant(orderDate, ZoneOffset.UTC);

            Instant startOfDayUtc = localDate.atStartOfDay(ZoneOffset.UTC).toInstant();
            Instant endOfDayUtc = localDate.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant();

            predicates.add(cb.between(root.get("orderDate"), startOfDayUtc, endOfDayUtc));
        }


        if (orderStatus != null) {
            predicates.add(cb.equal(root.get("status"), orderStatus));
        }

        if (orderType != null) {
            predicates.add(cb.equal(root.get("orderType"), orderType));
        }

        if (book != null) {
            Join<PurchaseOrder, Book> bookJoin = root.join("books", JoinType.LEFT);
            predicates.add(cb.equal(bookJoin, book));
        }

        predicates.add(cb.equal(root.type(), PurchaseOrder.class));

        query.select(root).where(cb.and(predicates.toArray(new Predicate[0]))).distinct(true);
        return entityManager.createQuery(query).getResultList();
    }
}
