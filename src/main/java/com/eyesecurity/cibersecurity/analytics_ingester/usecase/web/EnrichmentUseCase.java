package com.eyesecurity.cibersecurity.analytics_ingester.usecase.web;

import com.eyesecurity.cibersecurity.analytics_ingester.domain.web.EnrichedMaliciousActivity;
import com.eyesecurity.cibersecurity.analytics_ingester.domain.web.MaliciousActivity;

public interface EnrichmentUseCase {
    public EnrichedMaliciousActivity getEnrichment(MaliciousActivity maliciousActivity);
}
