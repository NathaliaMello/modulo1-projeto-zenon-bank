package br.com.zenon.vo;

import br.com.zenon.domain.Transaction;

import java.util.List;

public record IngestionResult(
        List<Transaction> transactions,
        List<String> errors
) { }
