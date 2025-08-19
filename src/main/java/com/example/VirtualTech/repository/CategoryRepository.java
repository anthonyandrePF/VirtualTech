package com.example.VirtualTech.repository;

import com.example.VirtualTech.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {}