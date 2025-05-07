package com.service;

import com.constant.AggregateType;
import com.constant.EventType;
import com.dao.OrderItemRepository;
import com.dao.OutboxEventRepository;
import com.dao.TestOrderRepository;
import com.domain.OrderItem;
import com.domain.OutboxEvent;
import com.domain.TestOrder;
import com.dto.ProductOrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final TestOrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OutboxEventRepository outboxEventRepository;

    @Transactional
    public void createOrder(List<ProductOrderRequest> productList) throws JsonProcessingException {
        // 1. 建立主單
        var order = TestOrder.builder().build();
        orderRepository.save(order);

        // 2. 建立子項目與計算總金額
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<Map<String, Object>> eventItems = new ArrayList<>();

        for (ProductOrderRequest req : productList) {
            BigDecimal unitPrice = req.getPrice();
            BigDecimal subTotal = unitPrice.multiply(BigDecimal.valueOf(req.getQuantity()));

            OrderItem orderItem = OrderItem.builder()
                    .orderId(order.getId())
                    .productId(req.getProductId())
                    .quantity(req.getQuantity())
                    .unitPrice(unitPrice)
                    .subTotal(subTotal)
                    .build();
            orderItemRepository.save(orderItem);

            totalAmount = totalAmount.add(subTotal);

            eventItems.add(Map.of(
                    "productId", req.getProductId(),
                    "quantity", req.getQuantity(),
                    "unitPrice", unitPrice,
                    "subTotal", subTotal
            ));
        }

        // 3. 更新訂單總金額
        order.setTotalAmount(totalAmount.intValue());
        orderRepository.save(order);

        // 4. 寫入 outbox_event
        Map<String, Object> payload = Map.of(
                "orderId", order.getId(),
                "items", eventItems
        );

        OutboxEvent event = OutboxEvent.builder()
                .aggregateType(AggregateType.ORDER)
                .aggregateId(order.getId())
                .eventType(EventType.ORDER_CREATED)
                .payload(new ObjectMapper().writeValueAsString(payload))
                .build();

        outboxEventRepository.save(event);
    }

    public List<TestOrder> getOrderList() {
        return orderRepository.findAll();
    }

    public TestOrder getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
    }

    public List<OrderItem> getOrderItemListFromOrder(Long orderId) {
        return orderItemRepository.findAllByOrderId(orderId);
    }

    public List<OrderItem> getOrderItemListFromProduct(Long productId) {
        return orderItemRepository.findAllByProductId(productId);
    }
}
