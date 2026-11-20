package br.com.zenon;

import br.com.zenon.dto.ReportSummary;
import br.com.zenon.report.TransactionReport;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class ReportMain {

    private static final String FILE_PATH = "data/PS_20174392719_1491204439457_log.csv";

    static void main(String[] args) {
        String language = args.length > 0 ? args[0] : "pt";
        Locale locale = switch (language) {
            case "pt" -> Locale.of("pt", "BR");
            case "en" -> Locale.US;
            default -> throw new IllegalArgumentException("Idioma não suportado: " + language + " (use pt ou en)");
        };

        ResourceBundle bundle = ResourceBundle.getBundle("report", locale);
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        TransactionReport report = new TransactionReport();
        ReportSummary summary = report.generateReportSummary(FILE_PATH);
        System.out.println(bundle.getString("report.total.lines") + " " + summary.totalLines());
        System.out.println(bundle.getString("report.total.frauds") + " " + summary.totalFrauds());
        System.out.println(bundle.getString("report.total.amount") + " " + formatter.format(summary.totalAmount()));
    }
}
