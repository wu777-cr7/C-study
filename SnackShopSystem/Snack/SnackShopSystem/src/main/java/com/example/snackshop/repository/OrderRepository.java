// OrderRepository.java
package com.example.snackshop.repository;

import com.example.snackshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserIdOrderByCreateTimeDesc(Long userId);
    List<Order> findAllByOrderByCreateTimeDesc();
    Order findByOrderNumber(String orderNumber);
}