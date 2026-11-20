package br.com.zenon.domain;

import java.math.BigDecimal;
import java.util.Objects;

public record Transaction(
        int step,
        TransactionType type,
        BigDecimal amount,
        TransactionCustomer origin,
        TransactionCustomer recipient,
        boolean isFraud,
        boolean isFlaggedFraud
) {

    public Transaction {
        if (step < 1) {
            throw new IllegalArgumentException("step should be positive: " + step);
        }
        Objects.requireNonNull(type, "type should not be null");
        if (null == amount || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("amount should be positive: " + amount);
        }
    }
}
