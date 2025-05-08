package com.debenziumdemo.serviceorder.service;

import com.debenziumdemo.serviceorder.config.DebeziumConnectorProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class DebeziumConnectorService {

    private final RestTemplate restTemplate;
    private final DebeziumConnectorProperties props;

    public void registerOutboxConnector() {
        String connectorUrl = props.getEndpointUrl() + "/" + props.getName();

        // 1. 正確刪除（加 Accept header）
        try {
            HttpHeaders deleteHeaders = new HttpHeaders();
            deleteHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
            HttpEntity<Void> deleteRequest = new HttpEntity<>(deleteHeaders);
            restTemplate.exchange(connectorUrl, HttpMethod.DELETE, deleteRequest, String.class);
            log.info("Deleted existing Debezium connector: {}", props.getName());
        } catch (Exception e) {
            log.warn("Delete connector failed or skipped: {}", e.getMessage());
        }

        // 2. 註冊 connector
        Map<String, Object> config = new HashMap<>();
        config.put("connector.class", props.getConnectorClass());
        config.put("database.hostname", props.getDatabase().getHostname());
        config.put("topic.prefix", props.getTopicPrefix());
        config.put("database.port", props.getDatabase().getPort());
        config.put("database.user", props.getDatabase().getUser());
        config.put("database.password", props.getDatabase().getPassword());
        config.put("database.server.id", props.getDatabase().getServerId());
        config.put("database.server.name", props.getDatabase().getServerName());
        config.put("database.include.list", props.getDatabase().getIncludeList());
        config.put("table.include.list", props.getTable().getIncludeList());
        config.put("schema.history.internal.kafka.bootstrap.servers", props.getSchemaHistory().getBootstrapServers());
        config.put("schema.history.internal.kafka.topic", props.getSchemaHistory().getTopic());
        config.put("tombstones.on.delete", String.valueOf(props.isTombstonesOnDelete()));
        config.put("include.schema.changes", String.valueOf(props.isIncludeSchemaChanges()));
        config.put("config.storage.topic", props.getConfigTopics().getConfigStorageTopic());
        config.put("offset.storage.topic", props.getConfigTopics().getOffsetStorageTopic());
        config.put("status.storage.topic", props.getConfigTopics().getStatusStorageTopic());
        config.put("snapshot.mode", "initial");
        config.put("transforms", "outbox");
        config.put("transforms.outbox.type", "io.debezium.transforms.outbox.EventRouter");
        config.put("transforms.outbox.route.by.field", "aggregate_type");
        config.put("transforms.outbox.route.topic.replacement", props.getTopicPrefix() + ".${routedByValue}");
        config.put("transforms.outbox.table.fields.additional.placement", "event_type:header:eventType");
        config.put("transforms.outbox.table.expand.json.payload", "true");
        config.put("transforms.outbox.table.field.name.mapping", String.join(",", List.of(
                "aggregateId:aggregate_id",
                "aggregateType:aggregate_type",
                "eventType:event_type",
                "createdAt:created_at"
        )));

        Map<String, Object> payload = new HashMap<>();
        payload.put("name", props.getName());
        payload.put("config", config);

        try {
            log.info("Debezium connector payload: {}", new ObjectMapper().writeValueAsString(payload));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(props.getEndpointUrl(), request, String.class);
            log.info("Debezium connector register response; status: {}, body: {}", response.getStatusCode(), response.getBody());
        } catch (HttpClientErrorException.Conflict e) {
            log.warn("Connector already exists: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Failed to register Debezium connector", e);
        }
    }
}
