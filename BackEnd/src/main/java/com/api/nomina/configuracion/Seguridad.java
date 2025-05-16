package com.api.nomina.configuracion;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class Seguridad {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
			.cors()
			.and()
			.csrf().disable()
			.authorizeHttpRequests()
			.requestMatchers("/empleado/**").permitAll()
			.anyRequest().authenticated();
		
		return http.build();
			
	}
	
	

}
