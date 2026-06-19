package com.examen.pedidos.service.impl;

import com.examen.pedidos.dto.request.ClienteRequest;
import com.examen.pedidos.dto.response.ClienteResponse;
import com.examen.pedidos.mapper.ClienteMapper;
import com.examen.pedidos.repository.ClienteRepository;
import com.examen.pedidos.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public ClienteResponse crear(ClienteRequest request) {
        var cliente = ClienteMapper.toEntity(request);
        return ClienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Override
    public ClienteResponse buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .map(ClienteMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
    }
}
