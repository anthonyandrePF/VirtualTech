package com.example.VirtualTech.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="BRAND")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Brand {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false, unique=true, length=100)
  private String name;
}