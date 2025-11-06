package com.hms2i.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private JWTFilter jwtFilter;

    public SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{


        //h(cd02
        http.csrf().disable().cors().disable();

        //haap
        //http.authorizeHttpRequests().anyRequest().permitAll();
        http.authorizeHttpRequests().requestMatchers("/api/v1/island/signup","/api/v1/island/login").permitAll();

        return http.build();

    }
}
