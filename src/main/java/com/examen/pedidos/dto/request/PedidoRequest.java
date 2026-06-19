package com.examen.pedidos.dto.request;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class PedidoRequest {
    private Long clienteId;
    private List<ItemPedidoRequest> items;
}
