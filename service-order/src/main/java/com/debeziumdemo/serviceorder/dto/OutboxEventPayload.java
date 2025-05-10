package com.debeziumdemo.serviceorder.dto;

public record OutboxEventPayload(
        Long id,
        String aggregateType,
        String aggregateId,
        String eventType,
        String payload,
        String createdAt
) {}
