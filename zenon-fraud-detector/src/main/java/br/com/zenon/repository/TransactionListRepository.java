package br.com.zenon.repository;

import br.com.zenon.domain.Transaction;
import br.com.zenon.ingest.TransactionIngestor;

import java.util.List;
import java.util.Optional;

public class TransactionListRepository implements TransactionRepository {

    private static final String FILE_PATH = "data/PS_20174392719_1491204439457_log.csv";
    private static final int MAX_LINES = 100000;
    private final List<Transaction> transactions;

    public TransactionListRepository() {
        this.transactions = new TransactionIngestor().ingest(FILE_PATH, MAX_LINES).transactions();
    }

    public Optional<Transaction> findTransactionByOriginCustomerName(String originCustomerName) {
        return transactions.stream()
                .filter(t -> t.origin().name().equals(originCustomerName))
                .findFirst();
    }

}
