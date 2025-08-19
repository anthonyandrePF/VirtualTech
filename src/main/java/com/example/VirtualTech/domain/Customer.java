package com.example.VirtualTech.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="CUSTOMER")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Customer {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name="FULL_NAME", nullable=false, length=150) private String fullName;
  @Column(unique=true, length=150) private String email;
  @Column(length=30) private String phone;
}



