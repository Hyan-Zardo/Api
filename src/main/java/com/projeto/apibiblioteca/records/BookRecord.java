package com.projeto.apibiblioteca.records;

public record BookRecord(String title,
                         String author,
                         String description,
                         String category,
                         Double price,
                         Integer quantity,
                         String conservation){
}
