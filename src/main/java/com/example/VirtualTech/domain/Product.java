package com.example.VirtualTech.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Entity @Table(name="PRODUCT")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Product {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank @Size(max=150) private String name;
  @NotBlank @Size(max=60)  private String sku;

  @NotNull @DecimalMin("0.0")
  @Column(precision=12, scale=2)
  private BigDecimal price;

  @NotNull @Min(0)
  private Integer stock;

  @ManyToOne(optional=false) @JoinColumn(name="CATEGORY_ID")
  private Category category;

  @ManyToOne(optional=false) @JoinColumn(name="BRAND_ID")
  private Brand brand;
}