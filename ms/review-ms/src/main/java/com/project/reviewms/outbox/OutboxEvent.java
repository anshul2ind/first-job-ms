package com.project.reviewms.outbox;

import com.project.reviewms.event.EventType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "outbox_events")
@Data
public class OutboxEvent {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private String aggregateId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String payload;

    @Enumerated(EnumType.STRING)
    private OutboxStatus status;

    private Instant createdAt;

    private Instant publishedAt;
}
