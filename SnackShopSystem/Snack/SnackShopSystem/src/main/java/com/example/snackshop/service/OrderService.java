package com.example.snackshop.service;

import com.example.snackshop.model.*;
import com.example.snackshop.repository.*;
import com.example.snackshop.util.OrderNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Autowired private OrderRepository orderRepository;
    @Autowired private OrderItemRepository orderItemRepository;
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private UserRepository userRepository;

    @Transactional
    public String createOrder(Long userId, String receiverName, String receiverPhone, String receiverAddress) throws Exception {
        // 获取购物车
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new Exception("购物车为空");
        }
        // 计算总金额并检查库存
        double total = 0.0;
        Map<Long, Integer> productQuantities = new HashMap<>();
        for (CartItem item : cartItems) {
            Product product = productRepository.findById(item.getProductId()).orElse(null);
            if (product == null || product.getStock() < item.getQuantity()) {
                throw new Exception("商品 " + (product != null ? product.getName() : "未知") + " 库存不足");
            }
            total += product.getPrice() * item.getQuantity();
            productQuantities.put(item.getProductId(), item.getQuantity());
        }
        // 生成订单
        Order order = new Order();
        order.setOrderNumber(OrderNumberGenerator.generate());
        order.setUserId(userId);
        order.setReceiverName(receiverName);
        order.setReceiverPhone(receiverPhone);
        order.setReceiverAddress(receiverAddress);
        order.setTotalAmount(total);
        order.setStatus("PENDING");
        order.setCreateTime(LocalDateTime.now());
        orderRepository.save(order);
        // 保存订单明细并扣减库存
        for (CartItem item : cartItems) {
            Product product = productRepository.findById(item.getProductId()).get();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItemRepository.save(orderItem);
            // 扣减库存
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }
        // 清空购物车
        cartItemRepository.deleteByUserId(userId);
        return order.getOrderNumber();
    }

    @Transactional
    public boolean payOrder(String orderNumber) throws Exception {
        Order order = orderRepository.findByOrderNumber(orderNumber);
        if (order == null || !"PENDING".equals(order.getStatus())) {
            throw new Exception("订单状态异常");
        }
        order.setStatus("PAID");
        order.setPayTime(LocalDateTime.now());
        orderRepository.save(order);
        return true;
    }

    @Transactional
    public boolean cancelOrder(String orderNumber) throws Exception {
        Order order = orderRepository.findByOrderNumber(orderNumber);
        if (order == null || !"PENDING".equals(order.getStatus())) {
            throw new Exception("只能取消待支付的订单");
        }
        // 恢复库存
        List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());
        for (OrderItem item : items) {
            Product product = productRepository.findById(item.getProductId()).orElse(null);
            if (product != null) {
                product.setStock(product.getStock() + item.getQuantity());
                productRepository.save(product);
            }
        }
        order.setStatus("CANCELLED");
        orderRepository.save(order);
        return true;
    }

    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserIdOrderByCreateTimeDesc(userId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByCreateTimeDesc();
    }

    public Order getOrderByNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    public List<OrderItem> getOrderItems(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }
}