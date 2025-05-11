package com.debeziumdemo.serviceorder.domain.model;

import com.debeziumdemo.serviceorder.domain.model.enums.AggregateType;
import com.debeziumdemo.serviceorder.domain.model.enums.EventType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "outbox_event")
public class OutboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "aggregateid", nullable = false)
    private Long aggregateId;

    @Enumerated(EnumType.STRING)
    @Column(name = "aggregatetype", nullable = false)
    private AggregateType aggregateType;

    @Enumerated(EnumType.STRING)
    @Column(name = "eventtype", nullable = false)
    private EventType eventType;

    @Lob
    @Column(nullable = false)
    private String payload;

    @Column(name = "createdat", nullable = false, updatable = false, insertable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Taipei")
    private LocalDateTime createdAt;

    @Builder.Default
    @Column(name = "processed", columnDefinition = "tinyint(1) default 0")
    private Boolean processed = false;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;
}
