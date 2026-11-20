package br.com.zenon.ingest;

import br.com.zenon.domain.Transaction;
import br.com.zenon.domain.TransactionCustomer;
import br.com.zenon.domain.TransactionType;
import br.com.zenon.dto.IngestionResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TransactionIngestor {


    private static final int EXPECTED_COLUMNS = 11;
    public IngestionResult ingest(String filePath, int maxLines) {

        List<Transaction> transactions = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath))) {
            reader.readLine();
            String line;
            int linesRead = 0;
            while (linesRead < maxLines && (line = reader.readLine()) != null) {
                linesRead++;

                try {
                    transactions.add(parseLine(line));
                } catch (IllegalArgumentException | NullPointerException e) {
                    errors.add("Erro: " + line + " | " + e);
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao ler o arquivo: " + filePath, e);
        }

        return new IngestionResult(transactions, errors);
    }

    public IngestionResult ingest(String filePath) {

        List<Transaction> transactions = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath))) {
            String line = reader.readLine();
            while (reader.readLine() != null) {
                try {
                    transactions.add(parseLine(line));
                } catch (IllegalArgumentException | NullPointerException e) {
                    errors.add("Erro: " + line + " | " + e);
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao ler o arquivo: " + filePath, e);
        }

        return new IngestionResult(transactions, errors);
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
