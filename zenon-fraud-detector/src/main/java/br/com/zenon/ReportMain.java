package br.com.zenon;

import br.com.zenon.dto.ReportSummary;
import br.com.zenon.report.TransactionReport;

public class ReportMain {

    private static final String FILE_PATH = "data/PS_20174392719_1491204439457_log.csv";

    static void main() {
        TransactionReport report = new TransactionReport();
        ReportSummary summary = report.generateReportSummary(FILE_PATH);
        System.out.println(summary);
    }
}
