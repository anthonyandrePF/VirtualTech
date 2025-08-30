package com.example.VirtualTech.security;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity @Table(name="APP_USER")
@Data @NoArgsConstructor @AllArgsConstructor
public class AppUser {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false, unique=true, length=50)
  private String username;

  @Column(nullable=false, length=120)
  private String password; // BCrypt

  @Column(nullable=false)
  private boolean enabled = true;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name="APP_USER_ROLES",
      joinColumns = @JoinColumn(name="USER_ID"),
      inverseJoinColumns = @JoinColumn(name="ROLE_ID"))
  private Set<Role> roles;
}
