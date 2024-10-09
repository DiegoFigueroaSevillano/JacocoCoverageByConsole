package org.example;

import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.tools.ExecFileLoader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class JacocoReportPrinter {

    public static void main(String[] args) throws IOException {
        File execFile = new File("build/jacoco/test.exec");
        ExecFileLoader execFileLoader = new ExecFileLoader();
        try (FileInputStream fis = new FileInputStream(execFile)) {
            execFileLoader.load(fis);
        }
        printCoverage(execFileLoader.getExecutionDataStore());
    }

    private static void printCoverage(ExecutionDataStore executionDataStore) throws IOException {
        CoverageBuilder coverageBuilder = new CoverageBuilder();
        Analyzer analyzer = new Analyzer(executionDataStore, coverageBuilder);
        analyzer.analyzeAll(new File("build/classes/java/main"));
        coverageBuilder.getClasses().forEach(cls -> {
            System.out.printf("Class: %s, Line Coverage: %s%%\n",
                    cls.getName(), calculateCoverage(cls.getLineCounter().getCoveredRatio()));
        });
    }

    private static String calculateCoverage(double ratio) {
        return String.format("%.2f", ratio * 100);
    }
}
