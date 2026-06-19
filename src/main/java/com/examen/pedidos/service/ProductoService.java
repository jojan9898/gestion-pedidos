package com.examen.pedidos.service;

import com.examen.pedidos.dto.request.ProductoRequest;
import com.examen.pedidos.dto.response.ProductoResponse;
import java.util.List;

public interface ProductoService {
    ProductoResponse crear(ProductoRequest request);
    List<ProductoResponse> listarTodos();
}
