package com.eyesecurity.cibersecurity.analytics_ingester.usecase.cli;

import com.eyesecurity.cibersecurity.analytics_ingester.domain.cli.MaliciousActivityLog;

public interface IngestionUseCase {
    void ingestMaliciousActivityLog(MaliciousActivityLog maliciousActivityLog);
}
