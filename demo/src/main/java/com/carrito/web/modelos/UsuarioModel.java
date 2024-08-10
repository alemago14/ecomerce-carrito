package com.carrito.web.modelos;

import lombok.Data;

@Data
public class UsuarioModel {
    
    private String id;
    private String username;
    private String password;
    private String confirmPassword;
    private String rol;
}
