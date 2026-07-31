package com.project.reviewms.event;

public enum EventType {

    COMPANY_RATING_UPDATED("company.rating.updated");

    private final String routingKey;

    EventType(String routingKey) {
        this.routingKey = routingKey;
    }

    public String routingKey() {
        return routingKey;
    }
}