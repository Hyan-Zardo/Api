package com.projeto.apibiblioteca.mappers;

import com.projeto.apibiblioteca.entities.Order;
import com.projeto.apibiblioteca.records.OrderRecord;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderRecord toOrderRecord(Order order);
    Order toOrder(OrderRecord orderRecord);
}