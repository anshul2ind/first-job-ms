package com.project.reviewms.outbox.impl;

import com.project.reviewms.event.CompanyRatingUpdatedEvent;
import com.project.reviewms.event.Eventable;
import com.project.reviewms.outbox.OutboxEvent;
import com.project.reviewms.outbox.OutboxRepository;
import com.project.reviewms.outbox.OutboxService;
import com.project.reviewms.outbox.OutboxStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OutboxServiceImpl implements OutboxService {

    private final OutboxRepository outboxRepository;
    private final ObjectMapper mapper;


    @Override
    @Transactional
    public void saveEvent(Eventable event) {
        var outboxEvent = new OutboxEvent();
        outboxEvent.setId(UUID.randomUUID());
        outboxEvent.setEventType(event.getEventType());
        try {
            outboxEvent.setPayload(mapper.writeValueAsString(event));
        } catch (Exception e) {

        }
        outboxEvent.setStatus(OutboxStatus.PENDING);
        outboxEvent.setCreatedAt(Instant.now());

        outboxRepository.save(outboxEvent);
    }
}
