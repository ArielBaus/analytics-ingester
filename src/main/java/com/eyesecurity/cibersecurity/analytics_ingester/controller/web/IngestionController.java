package com.eyesecurity.cibersecurity.analytics_ingester.controller.web;

import com.eyesecurity.cibersecurity.analytics_ingester.domain.web.MaliciousActivity;
import com.eyesecurity.cibersecurity.analytics_ingester.usecase.web.IngestMaliciousActivityUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingestion")
@ConditionalOnWebApplication
public class IngestionController {

    private final IngestMaliciousActivityUseCase ingestMaliciousActivityUseCase;

    @Autowired
    public IngestionController(IngestMaliciousActivityUseCase ingestMaliciousActivityUseCase) {
        this.ingestMaliciousActivityUseCase = ingestMaliciousActivityUseCase;
    }

    @PostMapping("/malicious_activity")
    public void ingestMaliciousActivity(
            @RequestBody MaliciousActivity maliciousActivity
    ) {
        ingestMaliciousActivityUseCase.ingestMaliciousActivity(maliciousActivity);
    }
}
