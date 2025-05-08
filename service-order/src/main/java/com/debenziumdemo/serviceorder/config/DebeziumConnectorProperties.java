package com.debenziumdemo.serviceorder.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "debezium.connector")
public class DebeziumConnectorProperties {
    private String name;
    private String connectorClass;
    private String endpointUrl;
    private String topicPrefix;

    private DatabaseConfig database;
    private TableConfig table;
    private KafkaConfig kafka;
    private ConfigTopics configTopics;
    private SchemaHistoryConfig schemaHistory;

    private boolean tombstonesOnDelete;
    private boolean includeSchemaChanges;

    @Data
    public static class DatabaseConfig {
        private String hostname;
        private int port;
        private String user;
        private String password;
        private String serverId;
        private String serverName;
        private String includeList;
    }

    @Data
    public static class TableConfig {
        private String includeList;
    }

    @Data
    public static class KafkaConfig {
        private String bootstrapServers;
    }

    @Data
    public static class ConfigTopics {
        private String configStorageTopic;
        private String offsetStorageTopic;
        private String statusStorageTopic;
        private String historyTopic;
    }

    @Data
    public static class SchemaHistoryConfig {
        private String bootstrapServers;
        private String topic;
    }
}
