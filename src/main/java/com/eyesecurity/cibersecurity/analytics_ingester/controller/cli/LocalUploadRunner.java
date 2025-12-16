package com.eyesecurity.cibersecurity.analytics_ingester.controller.cli;

import com.eyesecurity.cibersecurity.analytics_ingester.domain.cli.MaliciousActivityLog;
import com.eyesecurity.cibersecurity.analytics_ingester.usecase.cli.IngestionUseCase;
import com.eyesecurity.cibersecurity.analytics_ingester.usecase.cli.InputReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Component
@ConditionalOnNotWebApplication
public class LocalUploadRunner implements CommandLineRunner {

    public final String DELIMITER = ";";
    private final InputReader inputReader;
    private final IngestionUseCase ingestionUseCase;

    @Autowired
    LocalUploadRunner(
            InputReader inputReader,
            IngestionUseCase ingestionUseCase
    ) {
        this.inputReader = inputReader;
        this.ingestionUseCase = ingestionUseCase;
    }

    @Override
    public void run(String... args) throws Exception {
        AtomicReference<Integer> successes = new AtomicReference<>(0);
        List<ErrorEntry> errorEntries = new java.util.ArrayList<>(Collections.emptyList());

        try {
            inputReader
                    .read()
                    .skip(1)
                    .map(maliciousLog -> maliciousLog.split(DELIMITER))
                    .map(fields -> {
                        try {
                            return MaliciousActivityLog.fromStringArray(fields);
                        } catch (Exception e) {
                            errorEntries.add(new ErrorEntry(Arrays.toString(fields), e));
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .forEach(maliciousActivityLog -> {
                        try {
                            ingestionUseCase.ingestMaliciousActivityLog(maliciousActivityLog);
                            successes.getAndSet(successes.get() + 1);
                        } catch (Exception e) {
                            errorEntries.add(new ErrorEntry(maliciousActivityLog, e));
                        }
                    });
        } finally {
            System.out.printf("Processed %d entries successfully. %d errors.%n", successes.get(), errorEntries.size());

            errorEntries.forEach(errorEntry -> {
                System.out.printf("Line %s failed with the following error. %s.%n", errorEntry.fields, errorEntry.e.getMessage());
            });
        }
    }

    private record ErrorEntry(
            Object fields,
            Exception e
    ) {}


}
