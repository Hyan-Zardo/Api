package com.projeto.apibiblioteca.records;

import com.projeto.apibiblioteca.enums.BookConservation;

public record BookRecord(String title,
                         String author,
                         String description,
                         String category,
                         Double price,
                         Integer quantity,
                         BookConservation conservation){
}
