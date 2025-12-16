package com.eyesecurity.cibersecurity.analytics_ingester.domain.web;

public record EnrichedMaliciousActivity(
        Integer id,
        String asset,
        String ip,
        String category,
        String asn,
        Integer correlationId
) {
}
