package com.project.companyms.config;

import com.project.companyms.messaging.RabbitMQConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
     MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    TopicExchange companyExchange() {
        return new TopicExchange(RabbitMQConstants.EXCHANGE, true, false);
    }

    @Bean
    Queue companyRatingQueue() {
        return QueueBuilder.durable(RabbitMQConstants.COMPANY_RATING_UPDATED_QUEUE)
                .build();
    }

    @Bean
    Binding companyRatingBinding(Queue companyRatingQueue, TopicExchange companyExchange) {
        return BindingBuilder
                .bind(companyRatingQueue)
                .to(companyExchange)
                .with(RabbitMQConstants.COMPANY_RATING_UPDATED_ROUTING_KEY);

    }

    @Bean
    RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter) {

        RabbitTemplate rabbitTemplate =
                new RabbitTemplate(connectionFactory);

        rabbitTemplate.setMessageConverter(messageConverter);

        return rabbitTemplate;
    }
}
