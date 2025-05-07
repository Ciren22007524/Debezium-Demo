package com.dto;

public record DebeziumPayloadWrapper(
        DebeziumEnvelope payload
) {}
