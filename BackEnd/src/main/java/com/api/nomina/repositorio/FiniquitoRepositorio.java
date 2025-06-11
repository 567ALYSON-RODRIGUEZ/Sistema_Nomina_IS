package com.api.nomina.repositorio;

import com.api.nomina.modelo.EFiniquito;
import com.api.nomina.modelo.EPlanillaAguinaldo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FiniquitoRepositorio extends JpaRepository<EFiniquito, Integer> {

	@Procedure(name = "Finiquito.generarFiniquito")
	String generarFiniquito(@Param("id_empleado") int idEmpleado);
}
