package com.eyesecurity.cibersecurity.analytics_ingester.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ArchitectureTest {

    static final String PACKAGES_ROOT = "com.eyesecurity.cibersecurity.analytics_ingester";
    static JavaClasses classes;

    @BeforeAll
    static void setUp() {

        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_ARCHIVES)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
                .importPackages(PACKAGES_ROOT);
    }

    @Test
    public void checkOnionArchitecture() {
        Architectures
                .onionArchitecture()
                .adapter("Controller", PACKAGES_ROOT + ".controller..")
                .applicationServices(PACKAGES_ROOT + ".config..")
                .domainServices(PACKAGES_ROOT + ".usecase..")
                .domainModels(PACKAGES_ROOT + ".domain..")
                .check(classes);
    }
}
