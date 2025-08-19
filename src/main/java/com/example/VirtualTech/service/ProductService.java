package com.example.VirtualTech.service;

import com.example.VirtualTech.domain.Product;
import com.example.VirtualTech.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class ProductService {
  private final ProductRepository repo;

  public List<Product> list(String q){
    if (q == null || q.isBlank()) {
      return repo.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }
    String s = q.trim();
    return repo.findByNameContainingIgnoreCaseOrSkuContainingIgnoreCase(s, s);
  }

  public Product save(Product p){ return repo.save(p); }
  public Product get(Long id){ return repo.findById(id).orElseThrow(); }
  public void delete(Long id){ repo.deleteById(id); }

  // Helpers para SKU único
  public boolean skuExists(String sku){ return repo.existsBySkuIgnoreCase(sku); }
  public boolean skuExistsForOther(String sku, Long id){ return repo.existsBySkuIgnoreCaseAndIdNot(sku, id); }
}
