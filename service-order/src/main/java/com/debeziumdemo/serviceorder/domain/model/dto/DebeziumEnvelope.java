package com.debeziumdemo.serviceorder.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DebeziumEnvelope(
        @JsonProperty("before") Object before,
        @JsonProperty("after") OutboxEventPayload after
) {}
