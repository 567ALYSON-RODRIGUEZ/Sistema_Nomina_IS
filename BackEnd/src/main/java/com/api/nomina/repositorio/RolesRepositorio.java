package com.api.nomina.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.nomina.modelo.ERol;

@Repository
public interface RolesRepositorio extends JpaRepository<ERol,Integer> {

		@Procedure(name = "Rol.spRoles")
	    void spGestionRoles(
	        @Param("opc") String opc,
	        @Param("cod") Integer cod,
	        @Param("nom") String nom,
	        @Param("desc") String desc
	    );
		
		@org.springframework.data.jpa.repository.query.Procedure(name = "Rol.listarTodos")
	    List<ERol> listarTodos(
	    		@Param("opc") String opc,
		        @Param("cod") Integer cod,
		        @Param("nom") String nom,
		        @Param("desc") String desc
	    );
}
