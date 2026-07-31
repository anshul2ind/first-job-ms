package com.project.companyms.messaging;


public final class RabbitMQConstants {

    public static final String EXCHANGE = "company.events";

    public static final String COMPANY_RATING_UPDATED_QUEUE =
            "company.rating.updated.queue";

    public static final String COMPANY_RATING_UPDATED_ROUTING_KEY =
            "company.rating.updated";

    private RabbitMQConstants() {
    }
}
