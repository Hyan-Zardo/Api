package com.projeto.apibiblioteca.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OrderStatus {
    ESPERANDO_PAGAMENTO,
    PAGO,
    CANCELADO,
    ESPERANDO_RETORNO,
    FINALIZADO;

    @JsonCreator
    public static OrderStatus from(String value) {
        return OrderStatus.valueOf(value.toUpperCase());
    }
}
