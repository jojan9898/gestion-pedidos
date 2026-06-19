## API REST de Gestión de Pedidos

Alumno: Johan Alexis Alfaro Mejia

Proyecto Maven con Spring Boot que permite registrar clientes, productos y crear pedidos. Cada pedido puede tener varios productos (relación uno a muchos).

## Tecnologías

Java 21, Spring Boot 3.3.0, Maven, PostgreSQL, Spring Data JPA, Lombok, JUnit 5, Mockito

## Configuración de base de datos

Crear la base de datos en PostgreSQL:

    CREATE DATABASE db_pedidos;

Las credenciales van en application.properties (usuario: postgres, contraseña: admin).

## Cómo ejecutar

    mvn clean install
    mvn spring-boot:run

La aplicación corre en http://localhost:8085

## Endpoints

    POST   /api/clientes
    GET    /api/clientes/{id}
    POST   /api/productos
    GET    /api/productos
    POST   /api/pedidos
    GET    /api/pedidos/{id}
    GET    /api/pedidos/cliente/{clienteId}

## Diagrama de flujo del sistema

```mermaid
flowchart TD
    HTTP([Petición HTTP])

    HTTP -->|POST /api/clientes| CC[ClienteController]
    HTTP -->|GET /api/clientes/:id| CC
    HTTP -->|POST /api/productos| PC[ProductoController]
    HTTP -->|GET /api/productos| PC
    HTTP -->|POST /api/pedidos| PedC[PedidoController]
    HTTP -->|GET /api/pedidos/:id| PedC
    HTTP -->|GET /api/pedidos/cliente/:id| PedC

    CC --> CSI[ClienteServiceImpl]
    PC --> PSI[ProductoServiceImpl]
    PedC --> PedSI[PedidoServiceImpl]

    CSI --> CR[(ClienteRepository)]
    PSI --> PR[(ProductoRepository)]
    PedSI --> CR
    PedSI --> PR
    PedSI --> PedR[(PedidoRepository)]

    CR --> DB[(PostgreSQL - db_pedidos)]
    PR --> DB
    PedR --> DB

    PedSI -->|PedidoNotFoundException| GEH[GlobalExceptionHandler]
    PedSI -->|StockInsuficienteException| GEH
    PedSI -->|RuntimeException| GEH

    GEH -->|BaseResponse 404/400/500| HTTP
    CSI -->|BaseResponse 200/201| HTTP
    PSI -->|BaseResponse 200/201| HTTP
    PedSI -->|BaseResponse 200/201| HTTP
```

## Flujo de creación de pedido

```mermaid
flowchart TD
    A([POST /api/pedidos]) --> B{¿Existe el cliente?}
    B -->|No| E1[RuntimeException\nCliente no encontrado]
    B -->|Sí| C[Por cada item del pedido]

    C --> D{¿Existe el producto?}
    D -->|No| E2[RuntimeException\nProducto no encontrado]
    D -->|Sí| F{¿Cantidad > 0?}

    F -->|No| E3[RuntimeException\nCantidad inválida]
    F -->|Sí| G{¿Stock suficiente?}

    G -->|No| E4[StockInsuficienteException\n400 Bad Request]
    G -->|Sí| H[Calcular subtotal\nprecio x cantidad]

    H --> I[Descontar stock del producto]
    I --> J{¿Más items?}
    J -->|Sí| C
    J -->|No| K[Calcular total del pedido]

    K --> L[Crear Pedido\nestado = CREADO]
    L --> M[Guardar en base de datos]
    M --> N([BaseResponse 201\nPedido creado correctamente])

    E1 --> ERR([GlobalExceptionHandler\nBaseResponse con error])
    E2 --> ERR
    E3 --> ERR
    E4 --> ERR
```

## Ejemplos de uso

Crear cliente:

    POST /api/clientes
    {
      "nombre": "Juan",
      "apellido": "Perez",
      "dni": "12345678",
      "correo": "juan.perez@gmail.com"
    }

Crear producto:

    POST /api/productos
    {
      "nombre": "Teclado mecanico",
      "descripcion": "Teclado RGB",
      "precio": 150.00,
      "stock": 20
    }

Crear pedido:

    POST /api/pedidos
    {
      "clienteId": 1,
      "items": [
        { "productoId": 1, "cantidad": 2 },
        { "productoId": 2, "cantidad": 1 }
      ]
    }
