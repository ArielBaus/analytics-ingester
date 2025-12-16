package com.eyesecurity.cibersecurity.analytics_ingester.usecase.web;

import com.eyesecurity.cibersecurity.analytics_ingester.domain.web.EnrichedMaliciousActivity;
import com.eyesecurity.cibersecurity.analytics_ingester.domain.web.MaliciousActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnWebApplication
public class IngestionService implements IngestMaliciousActivityUseCase {

    private final EnrichmentUseCase enrichmentUseCase;
    private final AnalyticsUseCase analyticsUseCase;

    @Autowired
    public IngestionService(EnrichmentUseCase enrichmentUseCase, AnalyticsUseCase analyticsUseCase) {
        this.enrichmentUseCase = enrichmentUseCase;
        this.analyticsUseCase = analyticsUseCase;
    }

    @Override
    public void ingestMaliciousActivity(MaliciousActivity maliciousActivity) {
        EnrichedMaliciousActivity enrichedMaliciousActivity = enrichmentUseCase.getEnrichment(maliciousActivity);
        analyticsUseCase.pushMaliciousActivity(enrichedMaliciousActivity);
    }
}
