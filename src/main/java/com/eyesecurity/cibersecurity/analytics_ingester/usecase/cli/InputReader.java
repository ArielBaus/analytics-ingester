package com.eyesecurity.cibersecurity.analytics_ingester.usecase.cli;

import java.util.stream.Stream;

public interface InputReader {
    public Stream<String> read();
}
