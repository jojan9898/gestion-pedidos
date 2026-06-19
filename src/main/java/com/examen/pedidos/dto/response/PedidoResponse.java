package com.examen.pedidos.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponse {
    private Long id;
    private String cliente;
    private BigDecimal total;
    private String estado;
    private LocalDate fechaPedido;
    private List<DetallePedidoResponse> detalles;
}
