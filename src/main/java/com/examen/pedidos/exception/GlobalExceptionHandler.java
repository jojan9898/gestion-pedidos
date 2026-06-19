package com.examen.pedidos.exception;

import com.examen.pedidos.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PedidoNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handlePedidoNotFound(PedidoNotFoundException ex) {
        return ResponseEntity.status(404).body(
                BaseResponse.<Object>builder()
                        .codigo(404)
                        .mensaje(ex.getMessage())
                        .objeto(null)
                        .build()
        );
    }

    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<BaseResponse<Object>> handleStockInsuficiente(StockInsuficienteException ex) {
        return ResponseEntity.status(400).body(
                BaseResponse.<Object>builder()
                        .codigo(400)
                        .mensaje(ex.getMessage())
                        .objeto(null)
                        .build()
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse<Object>> handleRuntime(RuntimeException ex) {
        return ResponseEntity.status(500).body(
                BaseResponse.<Object>builder()
                        .codigo(500)
                        .mensaje(ex.getMessage())
                        .objeto(null)
                        .build()
        );
    }
}
