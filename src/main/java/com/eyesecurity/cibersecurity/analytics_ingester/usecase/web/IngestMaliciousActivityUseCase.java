package com.eyesecurity.cibersecurity.analytics_ingester.usecase.web;

import com.eyesecurity.cibersecurity.analytics_ingester.domain.web.MaliciousActivity;

public interface IngestMaliciousActivityUseCase {
    public void ingestMaliciousActivity(MaliciousActivity maliciousActivity);
}
