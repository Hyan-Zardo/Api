package com.projeto.apibiblioteca.controllers;

import com.projeto.apibiblioteca.entities.Book;
import com.projeto.apibiblioteca.mappers.BookMapper;
import com.projeto.apibiblioteca.records.BookRecord;
import com.projeto.apibiblioteca.repositories.BookRepository;
import com.projeto.apibiblioteca.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService service;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping
    public ResponseEntity<BookRecord> addBook(@RequestBody BookRecord bookRecord) {
        Book book = BookMapper.INSTANCE.toBook(bookRecord);
        service.addBook(bookRecord);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(book.getId()).toUri();
        return ResponseEntity.created(uri).body(bookRecord);
    }

    @GetMapping("{id}")
    public ResponseEntity<BookRecord> getBook(@PathVariable("id") UUID id) {
        BookRecord bookRecord = service.getBookDetails(id);
        return ResponseEntity.ok().body(bookRecord);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookRecord>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String author) {

        return ResponseEntity.ok(service.searchBooks(title, category, author));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletBook(@PathVariable("id") UUID id){
        service.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
