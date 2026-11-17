package br.com.zenon.domain;

import java.math.BigDecimal;
import java.util.Objects;

public record Transaction(
        int step,
        TypeEnum type,
        BigDecimal amount,
        String nameOrig,
        BigDecimal oldbalanceOrg,
        BigDecimal newbalanceOrig,
        String nameDest,
        BigDecimal oldbalanceDest,
        BigDecimal newbalanceDest,
        boolean isFraud,
        boolean isFlaggedFraud
) {

    public Transaction {
        Objects.requireNonNull(type, "Tipo não pode ser nulo");
        Objects.requireNonNull(amount, "Valor não pode ser nulo");
        Objects.requireNonNull(nameOrig, "Nome Origem não pode ser nulo");
        Objects.requireNonNull(oldbalanceOrg, "Saldo Antigo Origem não pode ser nulo");
        Objects.requireNonNull(newbalanceOrig, "Saldo Novo Origem não pode ser nulo");
        Objects.requireNonNull(nameDest, "Nome Destino não pode ser nulo");
        Objects.requireNonNull(oldbalanceDest, "Saldo Antigo Destino não pode ser nulo");
        Objects.requireNonNull(newbalanceDest, "Saldo Novo Destino não pode ser nulo");
    }
}
