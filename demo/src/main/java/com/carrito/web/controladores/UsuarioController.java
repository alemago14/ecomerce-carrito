package com.carrito.web.controladores;

import org.springframework.web.bind.annotation.RestController;

import com.carrito.web.entidades.Usuario;
import com.carrito.web.enumeraciones.Rol;
import com.carrito.web.modelos.UsuarioModel;
import com.carrito.web.servicios.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "Gestión de usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/nuevo")
    @Operation(summary = "Crear un nuevo usuario", description = "Guarda un uevo usuario en la base de datos a partir de un usuarioModel")
    public ResponseEntity<Object> nuevoUsuario(@RequestBody UsuarioModel usuario) {
        try {
            usuario.setId(null);
            usuarioService.guardarUsuario(usuario);
            return new ResponseEntity<>("Usuario creado", HttpStatus.CREATED);
        } catch (Exception e) {
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //@PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/listar")
    @Operation(summary = "Listar todos los usuarios", description = "Devuelve el listado de todos los usuarios en usuarios en la base de datos")
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @Operation(summary = "Obtener el usuario actual", description = "Retorna solamente el usario que esta autenticado en este momento.")
    @GetMapping("/actual")
    public Usuario getCurrentUser(Authentication authentication) {
        return (Usuario) authentication.getPrincipal();
    }

    @GetMapping("/esAdmin")
    public boolean esAdmin(Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();

        return usuario.getRol().equals(Rol.ADMINISTRADOR);
    }
    
    
    
    
    
}
