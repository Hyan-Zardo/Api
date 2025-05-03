package com.projeto.apibiblioteca.mappers;

import com.projeto.apibiblioteca.entities.Book;
import com.projeto.apibiblioteca.records.BookRecord;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookRecord toBookRecord(Book book);
    Book toBook(BookRecord bookRecord);


}