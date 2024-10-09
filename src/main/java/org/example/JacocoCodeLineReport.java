package org.example;

import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.ILine;
import org.jacoco.core.analysis.IMethodCoverage;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.tools.ExecFileLoader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class JacocoCodeLineReport {

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
        for (IClassCoverage classCoverage : coverageBuilder.getClasses()) {
            System.out.printf("Class: %s\n", classCoverage.getName());

            for (IMethodCoverage methodCoverage : classCoverage.getMethods()) {
                System.out.printf("  Method: %s, Line Range: %d-%d\n", methodCoverage.getName(),
                        methodCoverage.getFirstLine(), methodCoverage.getLastLine());
            }

            for (int i = classCoverage.getFirstLine(); i <= classCoverage.getLastLine(); i++) {
                ILine line = classCoverage.getLine(i);
                String status = getLineStatus(line);
                System.out.printf("    Line %d: %s\n", i, status);
            }
            System.out.println();
        }
    }

    private static String getLineStatus(ILine line) {
        switch (line.getStatus()) {
            case 0:
                return "Covered";
            case 1:
                return "Partially Covered";
            case 2:
                return "Not Covered";
            default:
                return "Unknown";
        }
    }
}
