package com.samael.springboot.backend.peliculas.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
@Component
public class WebSecurityConfig {
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
            .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests( authorize -> authorize
                .requestMatchers("/auth/login", "/auth/registro", "/images/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/usuarios").permitAll()//.hasRole("ADMIN") // 🔹 Solo ADMIN puede ver usuarios
                .requestMatchers(HttpMethod.GET, "/api/usuarios/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll() // 🔹 Solo los ADMIN pueden crear usuarios
                .requestMatchers(HttpMethod.POST, "/api/usuarios/verificar-password").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").permitAll() // 🔹 Solo los ADMIN pueden crear usuarios
                .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").permitAll() // 🔹 Solo los ADMIN pueden crear usuarios
                
              //Peliculas
                .requestMatchers(HttpMethod.GET, "/api/peliculas").permitAll() 
                .requestMatchers(HttpMethod.GET, "/api/peliculas/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/peliculas").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/peliculas/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/peliculas/**").permitAll()//hasRole("ADMIN")
              //Actores
                .requestMatchers(HttpMethod.GET, "/api/actores").permitAll() 
                .requestMatchers(HttpMethod.GET, "/api/actores/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/actores").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/actores/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/actores/**").permitAll()//hasRole("ADMIN")
              //Categories
                .requestMatchers(HttpMethod.GET, "/api/categories").permitAll() 
                .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/categories").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/categories/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/categories/**").permitAll()//hasRole("ADMIN")
              //Directores
                .requestMatchers(HttpMethod.GET, "/api/directores").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/directores/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/directores").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/directores/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/directores/**").permitAll()//hasRole("ADMIN")
              //Flags
                .requestMatchers(HttpMethod.GET, "/api/flags").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/flags/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/flags").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/flags/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/flags/**").permitAll()//hasRole("ADMIN")
              //Genres
                .requestMatchers(HttpMethod.GET, "/api/genres").permitAll() 
                .requestMatchers(HttpMethod.GET, "/api/genres/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/genres").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/genres/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/genres/**").permitAll()//hasRole("ADMIN")
              //Languages
                .requestMatchers(HttpMethod.GET, "/api/languages").permitAll() 
                .requestMatchers(HttpMethod.GET, "/api/languages/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/languages").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/languages/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/languages/**").permitAll()//hasRole("ADMIN")
              //Studios
                .requestMatchers(HttpMethod.GET, "/api/studios").permitAll() 
                .requestMatchers(HttpMethod.GET, "/api/studios/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/studios").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/studios/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/studios/**").permitAll()//hasRole("ADMIN")
              //Tarjetas
                .requestMatchers(HttpMethod.GET, "/api/tarjetas").permitAll() 
                .requestMatchers(HttpMethod.GET, "/api/tarjetas/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/tarjetas").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/tarjetas/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/tarjetas/**").permitAll()//hasRole("ADMIN")
                
              //Carrito
                .requestMatchers(HttpMethod.GET, "/api/carritos").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/carritos/usuario/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/carritos").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/carritos/**").permitAll()
                
             //Compra
                .requestMatchers(HttpMethod.GET, "/api/compras").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/compras/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/compras").permitAll()
            
            //Estadisticas
                .requestMatchers(HttpMethod.POST, "/api/estadisticas/diarias").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/estadisticas/semanales").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/estadisticas/mensuales").permitAll()
                
            //Tendencias
                .requestMatchers(HttpMethod.GET, "/api/tendencias/global").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/tendencias/mensual").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/tendencias/recomendacion").permitAll()
                
                .anyRequest().authenticated()
            );
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        // configuration.setAllowedOrigins(Arrays.asList("*")); // Si da problemas
        // cambiar por la siguiente linea
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Access-Control-Allow-Origin",
                "Access-Control-Request-Method", "Access-Control-Request-Headers", "Origin", "Cache-Control",
                "Content-Type", "Authorization"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}