package com.debenziumdemo.servicepoint.listener;

import com.debenziumdemo.servicepoint.constant.TransactionType;
import com.debenziumdemo.servicepoint.dao.PointTransactionRepository;
import com.debenziumdemo.servicepoint.domain.PointTransaction;
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

    private final PointTransactionRepository pointRepo;

    @KafkaListener(topics = "dbserver_order.outbox_event", groupId = "point-service")
    public void handleOrderCreated(ConsumerRecord<String, String> record) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode event = objectMapper.readTree(record.value());

        if (!"ORDER_CREATED".equals(event.get("eventType").asText())) return;

        Long orderId = event.get("orderId").asLong();
        int totalAmount = event.get("totalAmount").asInt();
        int earnedPoints = totalAmount / 10; // 假設 10 元 1 點

        PointTransaction txn = PointTransaction.builder()
                .orderId(orderId)
                .type(TransactionType.EARN)
                .points(earnedPoints)
                .description("購物回饋")
                .build();

        pointRepo.save(txn);
    }
}
