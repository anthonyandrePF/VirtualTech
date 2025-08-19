package com.example.VirtualTech.repository;

import com.example.VirtualTech.domain.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

  @EntityGraph(attributePaths = {"category","brand"})
  List<Product> findAll(Sort sort);

  @EntityGraph(attributePaths = {"category","brand"})
  List<Product> findByNameContainingIgnoreCaseOrSkuContainingIgnoreCase(String name, String sku);

  boolean existsBySkuIgnoreCase(String sku);
  boolean existsBySkuIgnoreCaseAndIdNot(String sku, Long id);
}
