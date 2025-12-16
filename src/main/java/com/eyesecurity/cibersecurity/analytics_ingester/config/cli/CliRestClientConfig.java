package com.eyesecurity.cibersecurity.analytics_ingester.config.cli;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@ConditionalOnNotWebApplication
public class CliRestClientConfig {

    @Bean
    public RestClient localRestClient(
            @Value("${client.ingester.url}") String ingesterUrl
    ) {
        return RestClient.builder()
                .baseUrl(ingesterUrl)
                .build();
    }
}
