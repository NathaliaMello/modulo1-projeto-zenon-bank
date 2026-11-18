package br.com.zenon.ingest;

import br.com.zenon.domain.Transaction;
import br.com.zenon.domain.TransactionCustomer;
import br.com.zenon.domain.TransactionType;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class TransactionIngestor {

    private static final int MAX_LINES = 1000;
    private static final int EXPECTED_COLUMNS = 11;
    public List<Transaction> ingest(String filePath) {

        Path path = Path.of(filePath);
        try (Stream<String> lines = Files.lines(path)) {
            return lines
                    .skip(1)
                    .limit(MAX_LINES)
                    .map(this::parseLine)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Transaction parseLine(String line) {
        String[] col = line.split(",");

        if(col.length != EXPECTED_COLUMNS) {
            throw new IllegalArgumentException("Número de linhas inválido para o arquivo");
        }

        TransactionCustomer origin = new TransactionCustomer(
                col[3], new BigDecimal(col[4]), new BigDecimal(col[5]));

        TransactionCustomer recipient = new TransactionCustomer(
                col[6], new BigDecimal(col[7]), new BigDecimal(col[8]));

        return new Transaction(
                Integer.parseInt(col[0]),
                TransactionType.valueOf(col[1]),
                new BigDecimal(col[2]),
                origin,
                recipient,
                parseFlag(col[9]),
                parseFlag(col[10])
        );
    }

    private static boolean parseFlag(String flag) {
        return switch (flag.trim()) {
            case "1" -> true;
            case "0" -> false;
            default -> throw new IllegalArgumentException("Flag inválida");
        };
    }
}
