package com.examen.pedidos.service;

import com.examen.pedidos.dto.request.ClienteRequest;
import com.examen.pedidos.dto.response.ClienteResponse;

public interface ClienteService {
    ClienteResponse crear(ClienteRequest request);
    ClienteResponse buscarPorId(Long id);
}
