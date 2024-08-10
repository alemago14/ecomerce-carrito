package com.carrito.web.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.carrito.web.entidades.Usuario;
import com.carrito.web.enumeraciones.Rol;
import com.carrito.web.modelos.UsuarioModel;
import com.carrito.web.repositorios.UsuarioRepository;
import com.carrito.web.servicios.UsuarioService;

@Controller
public class AuthController {
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String getMethodName() {
        return "login";
    }
    

    @GetMapping("/register")
    public String showRegistrationForm(ModelMap modelo) {
        UsuarioModel usuario = new UsuarioModel();
        modelo.addAttribute("usuario", usuario);
        return "register"; // Devuelve la página de registro
    }

    @PostMapping("/registrar")
    public String registerUser(@ModelAttribute("usuario") UsuarioModel usuario, Model model) {
        try {
            usuarioService.guardarUsuario(usuario);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "redirect:/login?registered"; // Redirigir a la página de login con un mensaje de éxito
    }
}
