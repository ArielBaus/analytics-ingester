package com.eyesecurity.cibersecurity.analytics_ingester.domain.cli;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record MaliciousActivityLog(
        Integer id,
        String assetName,
        String ip,
        LocalDateTime createdAt,
        String source,
        String category
) {
    private static final List<String> VALID_CATEGORIES = List.of(
            "contentinjection",
            "drivebycompromise",
            "exploitpublicfacingapplication",
            "externalremoteservices",
            "hardwareadditions",
            "phishing",
            "replicationthroughremovablemedia",
            "supplychaincompromise",
            "trustedrelationship",
            "validaccounts"
    );

    private static final Integer ID_INDEX = 0;
    private static final Integer ASSET_NAME_INDEX = 1;
    private static final Integer IP_INDEX = 2;
    private static final Integer CREATED_AT_INDEX = 3;
    private static final Integer SOURCE_INDEX = 4;
    private static final Integer CATEGORY_INDEX = 5;

    static public MaliciousActivityLog fromStringArray(String[] fields) {
        String category = sanitizeCategoryName(fields[CATEGORY_INDEX]);
        if (!VALID_CATEGORIES.contains(category)) {
            throw new RuntimeException(String.format("Category '%s' is not valid", category));
        }

        return new MaliciousActivityLog(
                Integer.parseInt(fields[ID_INDEX]),
                fields[ASSET_NAME_INDEX],
                fields[IP_INDEX],
                LocalDateTime.parse(fields[CREATED_AT_INDEX], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                fields[SOURCE_INDEX],
                category
        );
    }

    static private String sanitizeCategoryName(String category) {
        return category.toLowerCase()
                .replace(" ","")
                .replace("-","")
                .replace("_","");
    }

    @Override
    public String toString() {
        return String.format("[%d, %s, %s, %s, %s, %s]", id , assetName, ip, createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), source, category);
    }
}