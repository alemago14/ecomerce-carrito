package com.carrito.web.repositorios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.carrito.web.entidades.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, String>{
    
    @Query("SELECT SUM(c.total) FROM Carrito c WHERE c.usuario.id = :idUsuario AND c.fechaCompra BETWEEN :fechaInicio AND :fechaFin")
    double calcularTotalMes(@Param("idUsuario") String idUsuario, @Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    @Query("SELECT c FROM Carrito c WHERE c.usuario.id = :idUsuario")
    List<Carrito> misCarritos(@Param("idUsuario") String idUsuario);
}
