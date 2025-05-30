package com.debeziumdemo.serviceorder.domain.repository;

import com.debeziumdemo.serviceorder.domain.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrderId(Long orderId);
    List<OrderItem> findAllByProductId(Long productId);
}
