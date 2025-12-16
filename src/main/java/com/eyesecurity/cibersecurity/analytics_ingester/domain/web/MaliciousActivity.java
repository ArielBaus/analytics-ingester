package com.eyesecurity.cibersecurity.analytics_ingester.domain.web;

import java.time.LocalDateTime;

public record MaliciousActivity(
        Integer id,
        String assetName,
        String ip,
        LocalDateTime createdAt,
        String source,
        String category
) {
}
