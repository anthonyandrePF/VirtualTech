// DataInitializer.java
package com.example.VirtualTech.config;

import com.example.VirtualTech.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
  private final RoleRepository roleRepo;
  private final AppUserRepository userRepo;
  private final PasswordEncoder encoder;

  @Bean CommandLineRunner initUsers() {
    return args -> {
      var rAdmin = roleRepo.findByName("ROLE_ADMIN").orElseGet(() -> roleRepo.save(new Role(null,"ROLE_ADMIN")));
      var rUser  = roleRepo.findByName("ROLE_USER").orElseGet(() -> roleRepo.save(new Role(null,"ROLE_USER")));

      userRepo.findByUsername("admin").orElseGet(() ->
          userRepo.save(new AppUser(null, "admin", encoder.encode("admin123"), true, Set.of(rAdmin, rUser)))
      );

      userRepo.findByUsername("cliente").orElseGet(() ->
          userRepo.save(new AppUser(null, "cliente", encoder.encode("cliente123"), true, Set.of(rUser)))
      );
    };
  }
}
