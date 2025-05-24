package com.api.nomina.configuraciones;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import io.jsonwebtoken.security.Keys;

@Configuration
public class Middleware {

	@Value("${jwt.secret}")
	private String secret;
	
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
			.requestMatchers("/anticipo/**").permitAll()
			.requestMatchers("/bonificacion/**").permitAll()
			.requestMatchers("/asistencia/**").permitAll()
			.requestMatchers("/nomina/**").permitAll()
			.requestMatchers("/igss/**").permitAll()
			.requestMatchers("/detallenominaM/**").permitAll()
			.requestMatchers("/detallenominaQ/**").permitAll()
			.requestMatchers("/detallenominaS/**").permitAll()
			.requestMatchers("/usuario/login").permitAll()
			.anyRequest().authenticated()
			.and()
			.oauth2ResourceServer(oauth2 -> oauth2
	                .jwt(jwt -> jwt.decoder(jwtDecoder()))
	            );
		
		return http.build();
	}
	
	@Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(
            Keys.hmacShaKeyFor(secret.getBytes())
        ).build();
    }
}
 