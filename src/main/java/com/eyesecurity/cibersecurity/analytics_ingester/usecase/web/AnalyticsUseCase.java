package com.eyesecurity.cibersecurity.analytics_ingester.usecase.web;

import com.eyesecurity.cibersecurity.analytics_ingester.domain.web.EnrichedMaliciousActivity;

public interface AnalyticsUseCase {

    public void pushMaliciousActivity(EnrichedMaliciousActivity enrichedMaliciousActivity);
}
