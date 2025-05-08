package com.debenziumdemo.serviceproduct.listener;

import com.debenziumdemo.serviceproduct.dao.TestProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxEventListener {
    private final TestProductRepository productRepository;

    @KafkaListener(topics = "dbserver_order.outbox_event", groupId = "product-service")
    public void handleOrderCreated(ConsumerRecord<String, String> record) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode event = objectMapper.readTree(record.value());

        if (!"ORDER_CREATED".equals(event.get("eventType").asText())) return;

        for (JsonNode item : event.get("items")) {
            Long productId = item.get("productId").asLong();
            int quantity = item.get("quantity").asInt();

            productRepository.findById(productId).ifPresent(product -> {
                product.setStock(product.getStock() - quantity);
                productRepository.save(product);
            });
        }
    }
}
