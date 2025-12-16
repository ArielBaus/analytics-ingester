package com.eyesecurity.cibersecurity.analytics_ingester.config.web.enrichment;

import com.eyesecurity.cibersecurity.analytics_ingester.domain.web.EnrichedMaliciousActivity;
import com.eyesecurity.cibersecurity.analytics_ingester.domain.web.MaliciousActivity;
import com.eyesecurity.cibersecurity.analytics_ingester.usecase.web.EnrichmentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.resilience.annotation.EnableResilientMethods;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

@Component
@ConditionalOnWebApplication
public class EnrichmentClient implements EnrichmentUseCase {

    private final RestClient restClient;
    private final String uri;

    private final ObjectMapper objectMapper = JsonMapper.builder().build();

    @Autowired
    EnrichmentClient(
            RestClient restClient,
            @Value("${client.eye_security.enrichment.uri}") String uri
    ) {
        this.restClient = restClient;
        this.uri = uri;
    }

    @Override
    @Retryable(maxRetries = 5, delay = 100, jitter = 10, multiplier = 2, maxDelay = 1000)
    public EnrichedMaliciousActivity getEnrichment(MaliciousActivity maliciousActivity) {
        String response = restClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path(uri)
                        .build()
                )
                .body(maliciousActivity)
                .retrieve()
                .body(String.class);

        EnrichmentResource enrichmentResource = objectMapper.readValue(response, EnrichmentResource.class);

        return new EnrichedMaliciousActivity(
                maliciousActivity.id(),
                maliciousActivity.assetName(),
                maliciousActivity.ip(),
                enrichmentResource.asn(),
                enrichmentResource.category(),
                enrichmentResource.correlationId()
        );
    }
}
