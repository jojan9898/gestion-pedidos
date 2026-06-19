package com.examen.pedidos.service;

import com.examen.pedidos.dto.request.ItemPedidoRequest;
import com.examen.pedidos.dto.request.PedidoRequest;
import com.examen.pedidos.dto.response.PedidoResponse;
import com.examen.pedidos.entity.Cliente;
import com.examen.pedidos.entity.Pedido;
import com.examen.pedidos.entity.Producto;
import com.examen.pedidos.exception.PedidoNotFoundException;
import com.examen.pedidos.exception.StockInsuficienteException;
import com.examen.pedidos.repository.ClienteRepository;
import com.examen.pedidos.repository.PedidoRepository;
import com.examen.pedidos.repository.ProductoRepository;
import com.examen.pedidos.service.impl.PedidoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceImplTest {

    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private PedidoServiceImpl pedidoService;

    @Test
    void crearPedido_cuandoDatosSonValidos_retornaPedidoCreado() {
        // ARRANGE
        Cliente cliente = Cliente.builder()
                .id(1L).nombre("Juan").apellido("Perez").build();

        Producto producto = Producto.builder()
                .id(1L).nombre("Teclado").precio(new BigDecimal("150.00")).stock(20).build();

        ItemPedidoRequest item = new ItemPedidoRequest();
        item.setProductoId(1L);
        item.setCantidad(2);

        PedidoRequest request = new PedidoRequest();
        request.setClienteId(1L);
        request.setItems(List.of(item));

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any())).thenReturn(producto);
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(inv -> {
            Pedido p = inv.getArgument(0);
            p.setId(1L);
            p.setDetalles(new ArrayList<>(p.getDetalles()));
            return p;
        });

        // ACT
        PedidoResponse response = pedidoService.crear(request);

        // ASSERT
        assertNotNull(response);
        assertEquals("CREADO", response.getEstado());
        assertEquals(new BigDecimal("300.00"), response.getTotal());
        verify(pedidoRepository).save(any(Pedido.class));
    }

    @Test
    void crearPedido_cuandoStockEsInsuficiente_lanzaStockInsuficienteException() {
        // ARRANGE
        Cliente cliente = Cliente.builder()
                .id(1L).nombre("Juan").apellido("Perez").build();

        Producto producto = Producto.builder()
                .id(1L).nombre("Teclado").precio(new BigDecimal("150.00")).stock(1).build();

        ItemPedidoRequest item = new ItemPedidoRequest();
        item.setProductoId(1L);
        item.setCantidad(5);

        PedidoRequest request = new PedidoRequest();
        request.setClienteId(1L);
        request.setItems(List.of(item));

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        // ACT & ASSERT
        assertThrows(StockInsuficienteException.class, () -> pedidoService.crear(request));
        verify(pedidoRepository, never()).save(any());
    }

    @Test
    void buscarPedido_cuandoNoExiste_lanzaPedidoNotFoundException() {
        // ARRANGE
        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(PedidoNotFoundException.class, () -> pedidoService.buscarPorId(99L));
    }
}
