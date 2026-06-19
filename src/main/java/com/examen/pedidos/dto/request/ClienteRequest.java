package com.examen.pedidos.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteRequest {
    private String nombre;
    private String apellido;
    private String dni;
    private String correo;
}
