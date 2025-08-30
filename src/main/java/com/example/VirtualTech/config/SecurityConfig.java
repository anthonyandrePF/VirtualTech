// SecurityConfig.java
package com.example.VirtualTech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

  @Bean
  SecurityFilterChain filter(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/webjars/**","/css/**","/js/**","/images/**").permitAll()
        .requestMatchers("/login", "/error").permitAll()        
        .requestMatchers(HttpMethod.GET, "/", "/dashboard", "/products/**")
          .hasAnyRole("ADMIN","USER")
        .requestMatchers("/products/**").hasRole("ADMIN")       
        .anyRequest().authenticated()
      )
      .formLogin(f -> f
        .loginPage("/login").permitAll()                        
        .defaultSuccessUrl("/", true)
      )
      .logout(l -> l.logoutSuccessUrl("/login?logout").permitAll());

    return http.build();
  }
}