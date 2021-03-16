package com.example.cloudstreamoffset.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

import java.util.function.Function;

@Configuration
public class KafkaFunctionConfiguration {

    @Bean
    Function<String, String> hello() {
        return s -> {

            if (s == null)
                throw new RuntimeException("null");

            var hello = "Hello " + s;

            System.out.println(hello);

            return hello;
        };
    }

    @Bean
    Function<String, String> reverse() {
        return hello -> new StringBuilder(hello).reverse().toString();
    }

    @Bean
    Function<Message<String>, Message<String>> acknowledgment() {

        return message -> {
            var acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
            if (acknowledgment != null) {
                System.out.println("Acknowledgment " + message.getPayload());
                acknowledgment.acknowledge();
            }
            return message;
        };
    }

}
