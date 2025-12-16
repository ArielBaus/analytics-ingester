package com.eyesecurity.cibersecurity.analytics_ingester.config.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.backoff.FixedBackOff;
import org.springframework.web.client.RestClient;

@Configuration
@ConditionalOnWebApplication
public class RestClientConfig {

    @Bean
    public RestClient eyeSecurityRestClient(
            @Value("${client.eye_security.url}") String eyeSecurityUrl,
            @Value("${client.eye_security.auth}") String authorizationString
    ) {
        return RestClient.builder()
                .baseUrl(eyeSecurityUrl)
                .defaultHeader("Authorization", authorizationString)
                .build();
    }
}
