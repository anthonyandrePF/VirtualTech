package com.example.VirtualTech.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity @Table(name="ORDER_ITEM")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderItem {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional=false) @JoinColumn(name="ORDER_ID")
  private SaleOrder order;

  @ManyToOne(optional=false) @JoinColumn(name="PRODUCT_ID")
  private Product product;

  private Integer quantity;

  @Column(name="UNIT_PRICE", precision=12, scale=2)
  private BigDecimal unitPrice;
}