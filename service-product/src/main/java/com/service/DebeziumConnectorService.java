package com.service;

import com.config.DebeziumConnectorProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class DebeziumConnectorService {

    private final RestTemplate restTemplate;
    private final DebeziumConnectorProperties props;

    public void registerOutboxConnector() {
        Map<String, Object> config = new HashMap<>();
        config.put("connector.class", props.getClassName());
        config.put("database.hostname", props.getDatabase().getHostname());
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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        String url = props.getEndpointUrl();
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        log.info("Debezium connector register response; status: {}, body: {}", response.getStatusCode(), response.getBody());
    }
}
