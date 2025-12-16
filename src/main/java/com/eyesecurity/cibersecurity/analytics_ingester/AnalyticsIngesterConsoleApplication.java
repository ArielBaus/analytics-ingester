package com.eyesecurity.cibersecurity.analytics_ingester;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnNotWebApplication
public class AnalyticsIngesterConsoleApplication implements CommandLineRunner {
    @Override
    public void run(String... args) { }
}