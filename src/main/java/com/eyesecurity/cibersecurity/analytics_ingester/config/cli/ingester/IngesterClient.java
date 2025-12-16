package com.eyesecurity.cibersecurity.analytics_ingester.config.cli.ingester;

import com.eyesecurity.cibersecurity.analytics_ingester.domain.cli.MaliciousActivityLog;
import com.eyesecurity.cibersecurity.analytics_ingester.usecase.cli.IngestionUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@ConditionalOnNotWebApplication
public class IngesterClient implements IngestionUseCase {

    private RestClient restClient;
    private String uri;

    @Autowired
    IngesterClient(
            RestClient restClient,
            @Value("${client.ingester.malicious_activity.uri}") String uri
    ) {
         this.restClient = restClient;
         this.uri = uri;
    }

    @Override
    public void ingestMaliciousActivityLog(MaliciousActivityLog maliciousActivityLog) {
        restClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(uri)
                        .build()
                )
                .body(maliciousActivityLog)
                .retrieve()
                .toBodilessEntity();
    }
}
