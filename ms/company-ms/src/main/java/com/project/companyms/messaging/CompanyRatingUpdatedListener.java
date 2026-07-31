package com.project.companyms.messaging;

import com.project.companyms.CompanyService;
import com.project.companyms.event.CompanyRatingUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class CompanyRatingUpdatedListener {
    private final ObjectMapper objectMapper;
    private final CompanyService companyService;

    @RabbitListener(queues = RabbitMQConstants.COMPANY_RATING_UPDATED_QUEUE)
    public void receive(String message) {
        CompanyRatingUpdatedEvent event = objectMapper.readValue(message, CompanyRatingUpdatedEvent.class);

        companyService.updateRating(event)  ;

        log.info(
                "Updated company {} rating to {}",
                event.companyId(),
                event.averageRating()
        );
    }

}
