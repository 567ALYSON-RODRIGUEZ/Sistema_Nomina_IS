package com.api.nomina.configuraciones;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class Seguridad {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
			.cors()
			.and()
			.csrf().disable()
			.authorizeHttpRequests()
			.requestMatchers("/departamento/**").permitAll()
			.requestMatchers("/puesto/**").permitAll()
			.requestMatchers("/rol/**").permitAll()
			.requestMatchers("/empleado/**").permitAll()
			.requestMatchers("/empresa/**").permitAll()
			.requestMatchers("/usuario/**").permitAll()
			.anyRequest().authenticated();
		
		return http.build();
	}
}
 