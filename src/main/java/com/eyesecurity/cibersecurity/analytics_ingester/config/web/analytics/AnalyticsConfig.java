package com.eyesecurity.cibersecurity.analytics_ingester.config.web.analytics;

import com.eyesecurity.cibersecurity.analytics_ingester.domain.web.EnrichedMaliciousActivity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class AnalyticsConfig {

    @Bean
    public ScheduledExecutorService analyticsExecutorScheduler() {
        return Executors.newScheduledThreadPool(1);
    }

    @Bean
    public ConcurrentLinkedQueue<EnrichedMaliciousActivity> enrichedMaliciousActivityQueue() {
        return new ConcurrentLinkedQueue<>();
    }
}
