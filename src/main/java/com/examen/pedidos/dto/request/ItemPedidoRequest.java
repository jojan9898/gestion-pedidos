package com.examen.pedidos.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoRequest {
    private Long productoId;
    private Integer cantidad;
}
