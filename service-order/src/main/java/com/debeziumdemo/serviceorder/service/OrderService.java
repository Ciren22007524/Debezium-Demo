package com.debeziumdemo.serviceorder.service;

import com.debeziumdemo.serviceorder.constant.AggregateType;
import com.debeziumdemo.serviceorder.constant.EventType;
import com.debeziumdemo.serviceorder.dao.OrderItemRepository;
import com.debeziumdemo.serviceorder.dao.OutboxEventRepository;
import com.debeziumdemo.serviceorder.dao.TestOrderRepository;
import com.debeziumdemo.serviceorder.domain.OrderItem;
import com.debeziumdemo.serviceorder.domain.OutboxEvent;
import com.debeziumdemo.serviceorder.domain.TestOrder;
import com.debeziumdemo.serviceorder.dto.ProductOrderRequest;
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
        // 1. 初始化訂單物件（先不存 DB）
        var order = TestOrder.builder().build();

        // 2. 建立子項目與計算總金額
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<Map<String, Object>> eventItems = new ArrayList<>();

        // 注意：此時還拿不到 order.getId()，因為還沒 persist
        List<OrderItem> orderItems = new ArrayList<>();

        for (ProductOrderRequest req : productList) {
            BigDecimal unitPrice = req.getPrice();
            BigDecimal subTotal = unitPrice.multiply(BigDecimal.valueOf(req.getQuantity()));

            totalAmount = totalAmount.add(subTotal);

            orderItems.add(OrderItem.builder()
                    .productId(req.getProductId())
                    .quantity(req.getQuantity())
                    .unitPrice(unitPrice)
                    .subTotal(subTotal)
                    .build());

            eventItems.add(Map.of(
                    "productId", req.getProductId(),
                    "quantity", req.getQuantity(),
                    "unitPrice", unitPrice,
                    "subTotal", subTotal
            ));
        }

        // 3. 設定總金額後再 persist
        order.setTotalAmount(totalAmount.intValue());
        orderRepository.save(order); // 此時 totalAmount 不為 null

        // 4. 儲存子項目（現在可以取得 orderId）
        for (OrderItem item : orderItems) {
            item.setOrderId(order.getId());
            orderItemRepository.save(item);
        }

        // 5. 寫入 outbox_event
        Map<String, Object> payload = Map.of(
                "orderId", order.getId(),
                "totalAmount", totalAmount.intValue(),
                "items", eventItems
        );

        outboxEventRepository.save(OutboxEvent.builder()
                .aggregateType(AggregateType.ORDER)
                .aggregateId(order.getId())
                .eventType(EventType.ORDER_CREATED)
                .payload(new ObjectMapper().writeValueAsString(payload))
                .build());
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
