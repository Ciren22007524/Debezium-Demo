package com.debenziumdemo.serviceorder.dto;

public record OutboxEventPayload(
        Long id,
        String aggregateType,
        String aggregateId,
        String eventType,
        String payload,
        String createdAt
) {}
