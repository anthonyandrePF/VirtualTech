package com.example.VirtualTech.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity @Table(name="SALE_ORDER")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class SaleOrder {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name="ORDER_DATE") private LocalDateTime orderDate;

  @ManyToOne(optional=false) @JoinColumn(name="CUSTOMER_ID")
  private Customer customer;

  @Column(precision=14, scale=2) private BigDecimal total;

  @OneToMany(mappedBy="order", cascade=CascadeType.ALL, orphanRemoval=true)
  private List<OrderItem> items = new ArrayList<>();
}