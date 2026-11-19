package br.com.zenon.service;

import br.com.zenon.domain.Transaction;
import br.com.zenon.domain.TransactionType;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class FraudAnalyzer {

    public int countFrauds(List<Transaction> transactions) {
        return  transactions.stream().filter(Transaction::isFraud).toList().size();
    }

    public List<BigDecimal> topThreeFraudAmounts(List<Transaction> transactions) {
        return transactions.stream()
                .filter(Transaction::isFraud)
                .map(Transaction::amount).sorted(Comparator.reverseOrder())
                .limit(3)
                .toList();
    }

    public Set<String> topFiveFraudCustomers(List<Transaction> transactions) {
        return transactions.stream()
                .filter(Transaction::isFraud)
                .sorted(Comparator.comparing(Transaction::amount).reversed())
                .map(transaction -> transaction.origin().name())
                .distinct()
                .limit(5)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public BigDecimal totalFraudAmount(List<Transaction> transactions) {
        return transactions.stream()
                .filter(Transaction::isFraud)
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<TransactionType, Long> countFraudsByTransactionType(List<Transaction> transactions) {
        return transactions.stream()
                .filter(Transaction::isFraud)
                .collect(Collectors.groupingBy(Transaction::type, Collectors.counting()));
    }
}
