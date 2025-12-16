package com.eyesecurity.cibersecurity.analytics_ingester.domain.web;

public enum MaliciousActivityCategories {
    CONTENT_INJECTION("contentinjection"),
    DRIVE_BY_COMPROMISE("drivebycompromise"),
    EXPLOIT_PUBLIC_FACING_APPLICATION("exploitpublicfacingapplication"),
    EXTERNAL_REMOTE_SERVICES("externalremoteservices"),
    HARDWARE_ADDITIONS("hardwareadditions"),
    PHISHING("phishing"),
    REPLICATION_THROUGH_REMOVABLE_MEDIA("replicationthroughremovablemedia"),
    SUPPLY_CHAIN_COMPROMISE("supplychaincompromise"),
    TRUSTED_RELATIONSHIP("trustedrelationship"),
    VALID_ACCOUNTS("validaccounts");

    private String categoryName;

    MaliciousActivityCategories(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return categoryName;
    }
}
