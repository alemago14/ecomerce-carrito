package com.carrito.web.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carrito.web.entidades.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String>{
    
    Optional<Usuario> findByUsername(String username);
}
