package com.projeto.apibiblioteca.repositories;

import com.projeto.apibiblioteca.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByTitleOrCategoryOrAuthor(String title, String category, String author);
}
