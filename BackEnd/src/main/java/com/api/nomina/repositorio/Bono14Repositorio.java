package com.api.nomina.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.api.nomina.modelo.EBono14;

@Repository
public interface Bono14Repositorio extends JpaRepository<EBono14, Integer> {

	   List<EBono14> findByAnio(int anio);

	    @Procedure(name = "PlanillaBono14.generarBono14")
	    void generarPlanillaBono14(
	    		@Param("anio") Integer anio,
	    		@Param("accion") String accion,
	            @Param("id_planilla") Integer idPlanilla);
}
