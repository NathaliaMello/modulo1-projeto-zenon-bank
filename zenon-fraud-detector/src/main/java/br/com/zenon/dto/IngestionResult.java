package br.com.zenon.dto;

import br.com.zenon.domain.Transaction;

import java.util.List;

public record IngestionResult(
        List<Transaction> transactions,
        List<String> errors
) { }
