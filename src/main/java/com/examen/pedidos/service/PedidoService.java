package com.examen.pedidos.service;

import com.examen.pedidos.dto.request.PedidoRequest;
import com.examen.pedidos.dto.response.PedidoResponse;
import java.util.List;

public interface PedidoService {
    PedidoResponse crear(PedidoRequest request);
    PedidoResponse buscarPorId(Long id);
    List<PedidoResponse> buscarPorCliente(Long clienteId);
}
