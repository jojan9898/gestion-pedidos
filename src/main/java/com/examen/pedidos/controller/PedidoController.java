package com.examen.pedidos.controller;

import com.examen.pedidos.dto.request.PedidoRequest;
import com.examen.pedidos.dto.response.PedidoResponse;
import com.examen.pedidos.response.BaseResponse;
import com.examen.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<BaseResponse<PedidoResponse>> crear(@RequestBody PedidoRequest request) {
        PedidoResponse response = pedidoService.crear(request);
        return ResponseEntity.status(201).body(
                BaseResponse.<PedidoResponse>builder()
                        .codigo(201)
                        .mensaje("Pedido creado correctamente")
                        .objeto(response)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<PedidoResponse>> buscarPorId(@PathVariable Long id) {
        PedidoResponse response = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(
                BaseResponse.<PedidoResponse>builder()
                        .codigo(200)
                        .mensaje("Pedido encontrado")
                        .objeto(response)
                        .build()
        );
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<BaseResponse<List<PedidoResponse>>> buscarPorCliente(@PathVariable Long clienteId) {
        List<PedidoResponse> response = pedidoService.buscarPorCliente(clienteId);
        return ResponseEntity.ok(
                BaseResponse.<List<PedidoResponse>>builder()
                        .codigo(200)
                        .mensaje("Pedidos del cliente obtenidos correctamente")
                        .objeto(response)
                        .build()
        );
    }
}
