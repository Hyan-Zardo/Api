package com.projeto.apibiblioteca.mappers;

import com.projeto.apibiblioteca.entities.Order;
import com.projeto.apibiblioteca.entities.PurchaseOrder;
import com.projeto.apibiblioteca.entities.RentOrder;
import com.projeto.apibiblioteca.records.OrderRequest;
import com.projeto.apibiblioteca.records.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderRequest toOrderRequest(RentOrder order);
    OrderRequest toOrderRequest(PurchaseOrder order);
    OrderRequest toOrderRequest(OrderResponse orderResponse);
    OrderResponse toOrderResponse(OrderRequest orderRequest);
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "bookIds", expression = "java(order.getBooks().stream().map(com.projeto.apibiblioteca.entities.Book::getId).toList())")
    @Mapping(target = "orderStatus", source = "status")
    @Mapping(target = "withdrawDate", source = "withdrawDate")
    OrderResponse toOrderResponse(RentOrder order);
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "bookIds", expression = "java(order.getBooks().stream().map(com.projeto.apibiblioteca.entities.Book::getId).toList())")
    @Mapping(target = "orderStatus", source = "status")
    OrderResponse toOrderResponse(PurchaseOrder order);
    RentOrder toRentOrder(OrderRequest orderRequest);
    RentOrder toRentOrder(OrderResponse orderResponse);
    PurchaseOrder toPurchaseOrder(OrderRequest orderRequest);
    PurchaseOrder toPurchaseOrder(OrderResponse orderResponse);

}