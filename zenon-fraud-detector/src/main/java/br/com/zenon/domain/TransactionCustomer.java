package br.com.zenon.domain;

import java.math.BigDecimal;

public record TransactionCustomer (
        String name,
        BigDecimal oldbalance,
        BigDecimal newbalance
){ }
