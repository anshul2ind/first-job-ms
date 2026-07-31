package com.project.reviewms.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQPublisher {
    private final RabbitTemplate rabbitTemplate;

    public void publish(String routingKey, Object payload) {
        rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGE, routingKey, payload);
    }
}
