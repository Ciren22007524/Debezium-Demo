package com.debenziumdemo.serviceorder.service;

import com.debenziumdemo.serviceorder.config.DebeziumConnectorProperties;
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
            restTemplate.exchange(connectorUrl, HttpMethod.DELETE, null, String.class);
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
        config.put("database.history.kafka.bootstrap.servers", props.getKafka().getBootstrapServers());
        config.put("database.history.kafka.topic", props.getConfigTopics().getHistoryTopic());
        config.put("tombstones.on.delete", String.valueOf(props.isTombstonesOnDelete()));
        config.put("include.schema.changes", String.valueOf(props.isIncludeSchemaChanges()));
        config.put("config.storage.topic", props.getConfigTopics().getConfigStorageTopic());
        config.put("offset.storage.topic", props.getConfigTopics().getOffsetStorageTopic());
        config.put("status.storage.topic", props.getConfigTopics().getStatusStorageTopic());

        Map<String, Object> payload = new HashMap<>();
        payload.put("name", props.getName());
        payload.put("config", config);

        try {
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
