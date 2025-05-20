package com.projeto.apibiblioteca.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OrderType {
    ALUGUEL,
    COMPRA;

    @JsonCreator
    public static OrderType from(String value) {
        return OrderType.valueOf(value.toUpperCase());
    }
}

