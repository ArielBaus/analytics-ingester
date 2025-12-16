package com.eyesecurity.cibersecurity.analytics_ingester.config.web.enrichment;

public record EnrichmentResource(
            String asn,
            String category,
            Integer correlationId
    ) {}