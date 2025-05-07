package com.kafka;

import com.dto.DebeziumPayloadWrapper;
import com.dto.OutboxEventPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OutboxKafkaListener {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "dbserver_product.db_product.outbox_event")
    public void listen(String message) throws JsonProcessingException {
        DebeziumPayloadWrapper wrapper = objectMapper.readValue(message, DebeziumPayloadWrapper.class);
        OutboxEventPayload event = wrapper.payload().after();

        System.out.println("收到事件：" + event.eventType());
        System.out.println("內容：" + event.payload());

        // TODO: 根據 eventType 做對應處理，例如轉發通知、推送到 Redis、執行下游任務等
    }
}
