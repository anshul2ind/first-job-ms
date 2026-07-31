package com.project.reviewms.outbox;

import com.project.reviewms.messaging.RabbitMQPublisher;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxPublisher {

    private final OutboxRepository outboxRepository;
    private final RabbitMQPublisher rabbitPublisher;

    @Scheduled(fixedDelay = 15000)
    @Transactional
    public void publishPendingEvents() {

        List<OutboxEvent> events =
                outboxRepository.findTop100ByStatusOrderByCreatedAtAsc(
                        OutboxStatus.PENDING);
        events.stream().parallel().forEach(this::publish);
    }

    private void publish(OutboxEvent event) {

        rabbitPublisher.publish(
                event.getEventType().routingKey(),
                event.getPayload()
        );

        event.setStatus(OutboxStatus.PUBLISHED);
        event.setPublishedAt(Instant.now());

        outboxRepository.save(event);

        log.info("Published outbox event {}", event.getId());
    }
}