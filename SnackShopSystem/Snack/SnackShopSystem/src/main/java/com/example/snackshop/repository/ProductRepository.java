// ProductRepository.java
package com.example.snackshop.repository;

import com.example.snackshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatusOrderByIdDesc(String status);
}