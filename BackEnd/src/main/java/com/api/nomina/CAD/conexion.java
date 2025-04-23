package com.api.nomina.CAD;

import org.springframework.stereotype.Component;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;

@Component
public class conexion {
	
	 @PostConstruct
	    public void init() {
	        Dotenv dotenv = Dotenv.load();

	        System.setProperty("DB_URL", dotenv.get("DB_URL"));
	        System.setProperty("DB_USER", dotenv.get("DB_USER"));
	        System.setProperty("DB_PASS", dotenv.get("DB_PASS"));
	        System.setProperty("DB_POOL_MAX", dotenv.get("DB_POOL_MAX"));
	        System.setProperty("DB_POOL_MIN", dotenv.get("DB_POOL_MIN"));
	    }
}
