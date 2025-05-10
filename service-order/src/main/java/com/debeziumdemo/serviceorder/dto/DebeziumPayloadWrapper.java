package com.debeziumdemo.serviceorder.dto;

public record DebeziumPayloadWrapper(
        DebeziumEnvelope payload
) {}
