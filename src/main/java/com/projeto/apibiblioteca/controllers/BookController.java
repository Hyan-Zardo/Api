package com.projeto.apibiblioteca.controllers;

import com.projeto.apibiblioteca.entities.Book;
import com.projeto.apibiblioteca.enums.BookConservation;
import com.projeto.apibiblioteca.mappers.BookMapper;
import com.projeto.apibiblioteca.records.BookRecord;
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
    private BookService service;

    public BookController() {}

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
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) BookConservation conservation,
            @RequestParam(required = false) Integer quantity,
            @RequestParam(required = false) String identify){

        return ResponseEntity.ok(service.searchBooks(title, isbn, category, author, conservation, quantity, identify));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") UUID id){
        service.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<BookRecord> updateBook(@RequestBody BookRecord bookRecord) {
        service.updateBook(bookRecord, bookRecord.id());
        return ResponseEntity.noContent().build();
    }

}