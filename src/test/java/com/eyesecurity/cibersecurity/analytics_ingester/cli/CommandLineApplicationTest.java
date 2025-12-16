package com.eyesecurity.cibersecurity.analytics_ingester.cli;

import com.eyesecurity.cibersecurity.analytics_ingester.controller.cli.LocalUploadRunner;
import com.eyesecurity.cibersecurity.analytics_ingester.domain.cli.MaliciousActivityLog;
import com.eyesecurity.cibersecurity.analytics_ingester.usecase.cli.IngestionUseCase;
import com.eyesecurity.cibersecurity.analytics_ingester.usecase.cli.InputReader;
import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = "spring.main.web-application-type=NONE")
@ActiveProfiles("test")
public class CommandLineApplicationTest {

    @MockitoBean
    private InputReader inputReader;

    @MockitoBean
    private IngestionUseCase ingestionUseCase;

    @Autowired
    private LocalUploadRunner localUploadRunner;

    @Autowired
    private ResourceLoader resourceLoader;

    @BeforeEach
    public void before() {
        Mockito.reset(ingestionUseCase);
    }

    @Test
    public void testCommandLineApplication() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(resourceLoader.getResource("classpath:/cli/test_data").getInputStream()));
        when(inputReader.read()).thenReturn(reader.lines());

        doThrow(new RuntimeException("Keep an eye open"))
                .doNothing()
                .when(ingestionUseCase)
                .ingestMaliciousActivityLog(any());


        String output = SystemLambda.tapSystemOut(() -> {
                    localUploadRunner.run();
                }
        );

        assertEquals(resourceLoader.getResource("classpath:/cli/expected_output").getContentAsString(StandardCharsets.UTF_8), output);
        verify(ingestionUseCase, times(4)).ingestMaliciousActivityLog(any());
    }
}
