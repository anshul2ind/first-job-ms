package com.project.reviewms.outbox;

import com.project.reviewms.event.CompanyRatingUpdatedEvent;
import com.project.reviewms.event.Eventable;

public interface OutboxService {
   void saveEvent(Eventable event);
}