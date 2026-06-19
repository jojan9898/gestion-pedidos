package com.examen.pedidos.controller;

import com.examen.pedidos.dto.request.ProductoRequest;
import com.examen.pedidos.dto.response.ProductoResponse;
import com.examen.pedidos.response.BaseResponse;
import com.examen.pedidos.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<BaseResponse<ProductoResponse>> crear(@RequestBody ProductoRequest request) {
        ProductoResponse response = productoService.crear(request);
        return ResponseEntity.status(201).body(
                BaseResponse.<ProductoResponse>builder()
                        .codigo(201)
                        .mensaje("Producto registrado correctamente")
                        .objeto(response)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<ProductoResponse>>> listar() {
        List<ProductoResponse> response = productoService.listarTodos();
        return ResponseEntity.ok(
                BaseResponse.<List<ProductoResponse>>builder()
                        .codigo(200)
                        .mensaje("Productos obtenidos correctamente")
                        .objeto(response)
                        .build()
        );
    }
}
