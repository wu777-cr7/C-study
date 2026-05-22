package com.example.snackshop.service;

import com.example.snackshop.model.CartItem;
import com.example.snackshop.model.Product;
import com.example.snackshop.repository.CartItemRepository;
import com.example.snackshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<Map<String, Object>> getCartItemsWithProducts(Long userId) {
        List<CartItem> items = cartItemRepository.findByUserId(userId);
        List<Map<String, Object>> result = new ArrayList<>();
        for (CartItem item : items) {
            Product product = productRepository.findById(item.getProductId()).orElse(null);
            if (product != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("cartItem", item);
                map.put("product", product);
                result.add(map);
            }
        }
        return result;
    }

    public boolean addToCart(Long userId, Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null || product.getStock() < quantity) {
            return false;
        }
        CartItem existing = cartItemRepository.findByUserIdAndProductId(userId, productId);
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            cartItemRepository.save(existing);
        } else {
            CartItem item = new CartItem();
            item.setUserId(userId);
            item.setProductId(productId);
            item.setQuantity(quantity);
            item.setCreateTime(LocalDateTime.now());
            cartItemRepository.save(item);
        }
        return true;
    }

    @Transactional
    public void updateQuantity(Long userId, Long productId, int quantity) {
        CartItem item = cartItemRepository.findByUserIdAndProductId(userId, productId);
        if (item != null) {
            if (quantity <= 0) {
                cartItemRepository.deleteByUserIdAndProductId(userId, productId);
            } else {
                item.setQuantity(quantity);
                cartItemRepository.save(item);
            }
        }
    }

    @Transactional
    public void removeCartItem(Long userId, Long productId) {
        cartItemRepository.deleteByUserIdAndProductId(userId, productId);
    }

    @Transactional
    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}