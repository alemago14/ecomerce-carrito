package com.carrito.web.controladores;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/front")
public class FrontCrontroller {

    @GetMapping("/index")
    public String index() {
        return "index.html";
    }

    @GetMapping("/cambiarFecha")
    public String cambiarFecha() {
        return "fecha-form.html";
    }

    @GetMapping("/carrito")
    public String vercarrito() {
        return "carrito.html";
    }

    @GetMapping("/compras")
    public String compras() {
        return "compras.html";
    }

    //@PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/usuarios")
    public String verUsuarios() {
        return "usuarios.html";
    }
    
    
    
    
    
}
