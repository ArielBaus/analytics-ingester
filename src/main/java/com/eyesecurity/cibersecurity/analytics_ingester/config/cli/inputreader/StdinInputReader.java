package com.eyesecurity.cibersecurity.analytics_ingester.config.cli.inputreader;

import com.eyesecurity.cibersecurity.analytics_ingester.usecase.cli.InputReader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Stream;

@Component
@ConditionalOnNotWebApplication
public class StdinInputReader implements InputReader {
    @Override
    public Stream<String> read() {
        return new BufferedReader(new InputStreamReader(System.in)).lines();
    }
}
