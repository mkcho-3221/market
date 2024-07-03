package com.example.wanted.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
                .logout(LogoutConfigurer::disable)
                .authorizeHttpRequests(authorzie -> authorzie
                        .requestMatchers("/orders/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/members/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST,"/products").hasRole("USER")
                        .requestMatchers(HttpMethod.PATCH,"/products/**").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/products/**").hasRole("USER")
                        .requestMatchers("/products/**").hasRole("USER")
                        .requestMatchers("/*").permitAll()
                );

        return httpSecurity.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
