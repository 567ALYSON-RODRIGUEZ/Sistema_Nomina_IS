package com.api.nomina.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.nomina.modelo.EDepartamento;

@Repository
public interface DepartamentosRepositorio extends JpaRepository<EDepartamento,Integer>{

	@Procedure(name = "Departamento.spDepartamentos")
    void spGestionDeptos(
        @Param("opc") String opc,
        @Param("cod") Integer cod,
        @Param("nom") String nom,
        @Param("desc") String desc,
        @Param("status") String status
    );
	
	@org.springframework.data.jpa.repository.query.Procedure(name = "Departamento.listarTodos")
    List<EDepartamento> listarTodos(
        @Param("opc") String opc,
        @Param("cod") Integer cod,
        @Param("nom") String nom,
        @Param("desc") String desc,
        @Param("status") String status
    );
}
