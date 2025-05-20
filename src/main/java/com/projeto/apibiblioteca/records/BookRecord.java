package com.projeto.apibiblioteca.records;

import com.projeto.apibiblioteca.enums.BookConservation;

import java.util.UUID;

public record BookRecord(UUID id,
                         String title,
                         String author,
                         String isbn,
                         String description,
                         String category,
                         Double price,
                         Integer quantity,
                         BookConservation conservation){
}
