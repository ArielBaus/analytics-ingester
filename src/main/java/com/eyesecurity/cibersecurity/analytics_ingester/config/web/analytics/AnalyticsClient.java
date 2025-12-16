package com.eyesecurity.cibersecurity.analytics_ingester.config.web.analytics;

import com.eyesecurity.cibersecurity.analytics_ingester.domain.web.EnrichedMaliciousActivity;
import com.eyesecurity.cibersecurity.analytics_ingester.usecase.web.AnalyticsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import java.util.concurrent.*;

@Component
@ConditionalOnWebApplication
public class AnalyticsClient implements AnalyticsUseCase {

    private final RestClient restClient;
    private final Integer batchSize;
    private final String uri;
    private ConcurrentLinkedQueue<EnrichedMaliciousActivity> enrichedMaliciousActivityQueue;

    @Autowired
    AnalyticsClient(
            RestClient restClient,
            @Value("${client.eye_security.analytics.uri}") String uri,
            @Value("${client.eye_security.analytics.batch_size:20}") Integer batchSize,
            ScheduledExecutorService analyticsExecutorScheduler,
            ConcurrentLinkedQueue<EnrichedMaliciousActivity> enrichedMaliciousActivityQueue
    ) {
        this.restClient = restClient;
        this.batchSize = batchSize;
        this.uri = uri;
        analyticsExecutorScheduler.scheduleAtFixedRate(pushToAnalytics, 0, 10, TimeUnit.SECONDS);
    }

    @Override
    public void pushMaliciousActivity(EnrichedMaliciousActivity enrichedMaliciousActivity) {
        enrichedMaliciousActivityQueue.offer(enrichedMaliciousActivity);
    }

    Runnable pushToAnalytics = new Runnable() {
        public void run() {
            List<EnrichedMaliciousActivity> list = new ArrayList<>();

            while (list.size() < batchSize && !enrichedMaliciousActivityQueue.isEmpty()) {
                EnrichedMaliciousActivity enrichedMaliciousActivity = enrichedMaliciousActivityQueue.poll();
                if (enrichedMaliciousActivity != null) {
                    list.add(enrichedMaliciousActivity);
                }
            }

            if(!list.isEmpty()) {
                ResponseEntity<Void> response = restClient.post()
                        .uri(uriBuilder -> uriBuilder
                                .path(uri)
                                .build()
                        )
                        .body(list)
                        .retrieve()
                        .toBodilessEntity();
                System.out.printf("Processed %d entries with response: %s.%n", list.size(), response.getStatusCode());
            } else {
                System.out.println("No entries to process.");
            }
        }
    };
}
