package br.com.zenon.repository;

import br.com.zenon.domain.Transaction;

import java.util.Optional;

public interface TransactionRepository{
    Optional<Transaction> findTransactionByOriginCustomerName(String originCustomerName);
}
