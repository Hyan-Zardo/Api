package com.projeto.apibiblioteca.services;

import com.projeto.apibiblioteca.entities.Book;
import com.projeto.apibiblioteca.mappers.BookMapper;
import com.projeto.apibiblioteca.records.BookRecord;
import com.projeto.apibiblioteca.repositories.BookRepository;
import com.projeto.apibiblioteca.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public void addBook(BookRecord bookRecord){
        Book book = BookMapper.INSTANCE.toBook(bookRecord);
        bookRepository.save(book);
    }

    public void deleteBook(UUID bookId){
        if (bookRepository.existsById(bookId)){
            bookRepository.deleteById(bookId);
        }else {
            throw new NotFoundException("Livro não encontrado");
        }
    }

    public void updateBook(BookRecord bookRecord, UUID bookId){
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Livro não encontrado"));
        updateData(book, bookRecord);
        bookRepository.save(book);
    }


    public BookRecord getBookDetails(UUID bookId){
        if (bookRepository.existsById(bookId)){
            Book book = bookRepository.getReferenceById(bookId);
            return BookMapper.INSTANCE.toBookRecord(book);
        }else {
            throw new NotFoundException("Livro não encontrado");
        }
    }

    public List<BookRecord> searchBooks(String title, String category, String author) {
        List<Book> books = bookRepository.findByTitleOrCategoryOrAuthor(title, category, author);
        return books.stream().map(BookMapper.INSTANCE::toBookRecord).collect(Collectors.toList());
    }

    private void updateData(Book book, BookRecord bookRecord){
        book.setTitle(bookRecord.title());
        book.setAuthor(bookRecord.author());
        book.setDescription(bookRecord.description());
        book.setCategory(bookRecord.category());
        book.setPrice(bookRecord.price());
        book.setQuantity(bookRecord.quantity());
        book.setConservation(bookRecord.conservation());
    }


}
