package com.examen.pedidos.controller;

import com.examen.pedidos.dto.request.ClienteRequest;
import com.examen.pedidos.dto.response.ClienteResponse;
import com.examen.pedidos.response.BaseResponse;
import com.examen.pedidos.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<BaseResponse<ClienteResponse>> crear(@RequestBody ClienteRequest request) {
        ClienteResponse response = clienteService.crear(request);
        return ResponseEntity.status(201).body(
                BaseResponse.<ClienteResponse>builder()
                        .codigo(201)
                        .mensaje("Cliente registrado correctamente")
                        .objeto(response)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ClienteResponse>> buscarPorId(@PathVariable Long id) {
        ClienteResponse response = clienteService.buscarPorId(id);
        return ResponseEntity.ok(
                BaseResponse.<ClienteResponse>builder()
                        .codigo(200)
                        .mensaje("Cliente encontrado")
                        .objeto(response)
                        .build()
        );
    }
}
