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
    WHERE (:title IS NULL OR UPPER(b.title) LIKE CONCAT('%', UPPER(:title), '%'))
      AND (:isbn IS NULL OR b.isbn = :isbn)
      AND (:category IS NULL OR UPPER(b.category) LIKE CONCAT('%', UPPER(:category), '%'))
      AND (:author IS NULL OR UPPER(b.author) LIKE CONCAT('%', UPPER(:author), '%'))
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
