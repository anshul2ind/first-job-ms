package com.project.reviewms.event;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record CompanyRatingUpdatedEvent (
        UUID eventId,
        Long companyId,
        Double averageRating,
        Long reviewCount,
        Instant occurredAt
)  implements Eventable

{
    @Override
    public EventType getEventType() {
        return EventType.COMPANY_RATING_UPDATED;
    }
}
