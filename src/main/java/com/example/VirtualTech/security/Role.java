// Role.java
package com.example.VirtualTech.security;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="ROLE")
@Data @NoArgsConstructor @AllArgsConstructor
public class Role {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false, unique=true, length=50)
  private String name; // "ROLE_ADMIN", "ROLE_USER"
}
