package br.com.zenon.domain;

import java.math.BigDecimal;

public record TransactionCustomer (
        String name,
        BigDecimal oldbalance,
        BigDecimal newbalance
){

    public TransactionCustomer {

        if (null == name || name.isBlank()) {
            throw new IllegalArgumentException("name should not be empty");
        }

        if (null == oldbalance || oldbalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("oldBalance should be positive: " + oldbalance);
        }

        if (null == newbalance || newbalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("newBalance should be positive: " + newbalance);
        }
    }
}
