package com.carrito.web.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carrito.web.entidades.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, String>{
    
}
