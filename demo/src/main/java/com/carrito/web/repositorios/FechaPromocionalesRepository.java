package com.carrito.web.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carrito.web.entidades.FechasPromocionales;

@Repository
public interface FechaPromocionalesRepository extends JpaRepository<FechasPromocionales, String>{
    
}
