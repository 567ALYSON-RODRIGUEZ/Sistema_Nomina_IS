package com.api.nomina.repositorio;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.nomina.modelo.EPuesto;

@Repository
public interface PuestosRepositorio extends JpaRepository<EPuesto,Integer>{
	@Procedure(name = "Puesto.spPuestos")
    void spGestionPuestos(
        @Param("opc") String opc,
        @Param("cod") Integer cod,
        @Param("nom") String nom,
        @Param("base") BigDecimal base, 
        @Param("nivel") String nivel,
        @Param("status") String status
    );
	
	@org.springframework.data.jpa.repository.query.Procedure(name = "Puesto.listarTodos")
    List<EPuesto> listarTodos(
    		@Param("opc") String opc,
            @Param("cod") Integer cod,
            @Param("nom") String nom,
            @Param("base") BigDecimal base, 
            @Param("nivel") String nivel,
            @Param("status") String status
    );
}
