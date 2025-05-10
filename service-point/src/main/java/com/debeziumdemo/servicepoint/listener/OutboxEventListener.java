package com.debeziumdemo.servicepoint.listener;

import com.debeziumdemo.servicepoint.constant.TransactionType;
import com.debeziumdemo.servicepoint.dao.PointTransactionRepository;
import com.debeziumdemo.servicepoint.domain.PointTransaction;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxEventListener {

    private final PointTransactionRepository pointRepo;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "db_order.ORDER", groupId = "point-service")
    public void handleOrderCreated(
            @Payload String payload,
            @Header("eventType") String eventType,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic
    ) throws Exception {
        log.info("收到事件：[{}]，來自 topic：[{}], 原始 payload: {}", eventType, topic, payload);

        if (!"ORDER_CREATED".equals(eventType)) return;

        JsonNode root = objectMapper.readTree(payload);
        JsonNode event = root.get("payload");

        Long orderId = event.get("orderId").asLong();
        int totalAmount = event.get("totalAmount").asInt();
        int earnedPoints = totalAmount / 10;

        PointTransaction txn = PointTransaction.builder()
                .orderId(orderId)
                .type(TransactionType.EARN)
                .points(earnedPoints)
                .description("購物回饋")
                .build();

        pointRepo.save(txn);
        log.info("儲值點數完成：orderId={}, points={}", orderId, earnedPoints);
    }
}
