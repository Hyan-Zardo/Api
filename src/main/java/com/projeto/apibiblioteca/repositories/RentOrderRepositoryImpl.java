package com.projeto.apibiblioteca.repositories;

import com.projeto.apibiblioteca.entities.Book;
import com.projeto.apibiblioteca.entities.RentOrder;
import com.projeto.apibiblioteca.entities.User;
import com.projeto.apibiblioteca.enums.OrderStatus;
import com.projeto.apibiblioteca.enums.OrderType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RentOrderRepositoryImpl implements RentOrderRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RentOrder> findByFilters(User user, Instant orderDate, LocalDate withdrawDate, LocalDate returnDate,
                                         OrderStatus orderStatus, OrderType orderType, Book book) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<RentOrder> query = cb.createQuery(RentOrder.class);
        Root<RentOrder> root = query.from(RentOrder.class);
        root.fetch("books", JoinType.LEFT); // se quiser carregar os livros
        List<Predicate> predicates = new ArrayList<>();

        if (user != null) {
            predicates.add(cb.equal(root.get("user"), user));
        }

        if (orderDate != null) {
            Instant start = orderDate.truncatedTo(ChronoUnit.SECONDS);
            Instant end = start.plus(1, ChronoUnit.SECONDS);
            predicates.add(cb.between(root.get("orderDate"), start, end));
        }

        if (withdrawDate != null) {
            predicates.add(cb.equal(root.get("withdrawDate"), withdrawDate));
        }

        if (returnDate != null) {
            predicates.add(cb.equal(root.get("returnDate"), returnDate));
        }

        if (orderStatus != null) {
            predicates.add(cb.equal(root.get("status"), orderStatus));
        }

        if (orderType != null) {
            predicates.add(cb.equal(root.get("orderType"), orderType));
        }

        if (book != null) {
            Join<RentOrder, Book> bookJoin = root.join("books", JoinType.LEFT);
            predicates.add(cb.equal(bookJoin, book));
        }

        predicates.add(cb.equal(root.type(), RentOrder.class));

        query.select(root).where(cb.and(predicates.toArray(new Predicate[0]))).distinct(true);
        return entityManager.createQuery(query).getResultList();
    }
}
