package com.debeziumdemo.serviceorder.domain.model.dto;

public record DebeziumPayloadWrapper(
        DebeziumEnvelope payload
) {}
