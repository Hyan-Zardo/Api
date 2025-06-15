package com.projeto.apibiblioteca.repositories;

import com.projeto.apibiblioteca.entities.Book;
import com.projeto.apibiblioteca.enums.BookConservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    @Query("""
    SELECT b FROM Book b
    WHERE (COALESCE(:title, '') = '' OR UPPER(b.title) LIKE UPPER(CONCAT('%', :title, '%')))
        AND (COALESCE(:isbn, '') = '' OR b.isbn = :isbn)
        AND (COALESCE(:category, '') = '' OR UPPER(b.category) LIKE UPPER(CONCAT('%', :category, '%')))
        AND (COALESCE(:author, '') = '' OR UPPER(b.author) LIKE UPPER(CONCAT('%', :author, '%')))
        AND (:conservation IS NULL OR b.conservation = :conservation)
        AND (:quantity IS NULL OR b.quantity = :quantity)
""")
    List<Book> findByFilters(
            @Param("title") String title,
            @Param("isbn") String isbn,
            @Param("category") String category,
            @Param("author") String author,
            @Param("conservation") BookConservation conservation,
            @Param("quantity") Integer quantity
    );

}
