package br.com.zenon.report;

import br.com.zenon.dto.ReportSummary;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class TransactionReport {

    private static class Summary {
        long count;
        long frauds;
        BigDecimal amount = BigDecimal.ZERO;
    }

    public ReportSummary generateReportSummary(String filePath) {
       Summary summary = new Summary();
        try (Stream<String> lines = Files.lines(Path.of(filePath)))  {
            lines.skip(1).forEach(line -> {
                String[] col = line.split(",");
                summary.count++;
                if (col[9].equals("1")) {
                    summary.frauds++;
                }
                summary.amount = summary.amount.add(new BigDecimal(col[2]));
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ReportSummary(summary.count, summary.frauds, summary.amount);
    }
}
