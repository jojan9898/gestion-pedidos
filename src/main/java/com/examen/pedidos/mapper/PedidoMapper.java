package com.examen.pedidos.mapper;

import com.examen.pedidos.dto.response.DetallePedidoResponse;
import com.examen.pedidos.dto.response.PedidoResponse;
import com.examen.pedidos.entity.Pedido;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoMapper {

    public static PedidoResponse toResponse(Pedido pedido) {
        List<DetallePedidoResponse> detalles = pedido.getDetalles() == null ? List.of() :
                pedido.getDetalles().stream()
                        .map(d -> DetallePedidoResponse.builder()
                                .productoId(d.getProductoId())
                                .nombreProducto(d.getNombreProducto())
                                .cantidad(d.getCantidad())
                                .precioUnitario(d.getPrecioUnitario())
                                .subtotal(d.getSubtotal())
                                .build())
                        .collect(Collectors.toList());

        String nombreCliente = pedido.getCliente().getNombre() + " " + pedido.getCliente().getApellido();

        return PedidoResponse.builder()
                .id(pedido.getId())
                .cliente(nombreCliente)
                .total(pedido.getTotal())
                .estado(pedido.getEstado())
                .fechaPedido(pedido.getFechaPedido())
                .detalles(detalles)
                .build();
    }
}
