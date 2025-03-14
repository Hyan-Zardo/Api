package com.projeto.apibiblioteca.records;

import com.projeto.apibiblioteca.enums.OrderStatus;
import com.projeto.apibiblioteca.enums.OrderType;

import java.util.List;

public record OrderRecord(UserRecord user,
                          List<BookRecord> books,
                          OrderType type,
                          OrderStatus status){
}
