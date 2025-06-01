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
//    @Query("""
//    SELECT b FROM Book b
//    WHERE (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')))
//      AND (:isbn IS NULL OR LOWER(b.isbn) LIKE LOWER(CONCAT('%', :isbn, '%')))
//      AND (:category IS NULL OR LOWER(b.category) LIKE LOWER(CONCAT('%', :category, '%')))
//      AND (:author IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%')))
//      AND (:conservation IS NULL OR b.conservation = :conservation)
//      AND (:quantity IS NULL OR b.quantity = :quantity)
//""")
    List<Book> findByFilters(
            @Param("title") String title,
            @Param("isbn") String author,
            @Param("category") String publisher,
            @Param("author") String category,
            @Param("conservation") BookConservation conservation,
            @Param("quantity") Integer edition
    );

}
