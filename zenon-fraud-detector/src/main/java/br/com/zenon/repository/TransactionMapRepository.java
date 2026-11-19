package br.com.zenon.repository;

import br.com.zenon.domain.Transaction;
import br.com.zenon.ingest.TransactionIngestor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TransactionMapRepository implements TransactionRepository{

    private static final String FILE_PATH = "data/PS_20174392719_1491204439457_log.csv";
    private static final int MAX_LINES = 100000;
    private final Map<String, Transaction> transactions = new HashMap<>();


    public TransactionMapRepository() {
        List<Transaction> transactionsList = new TransactionIngestor().ingest(FILE_PATH, MAX_LINES).transactions();

        for(Transaction transaction : transactionsList) {
            this.transactions.putIfAbsent(transaction.origin().name(), transaction);
        }
    }

    @Override
    public Optional<Transaction> findTransactionByOriginCustomerName(String originCustomerName) {
        return Optional.ofNullable(transactions.get(originCustomerName));
    }
}
