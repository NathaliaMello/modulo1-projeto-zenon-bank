package br.com.zenon;

import br.com.zenon.domain.Transaction;
import br.com.zenon.domain.TransactionCustomer;
import br.com.zenon.domain.TransactionType;
import br.com.zenon.ingest.TransactionIngestor;
import br.com.zenon.repository.TransactionListRepository;
import br.com.zenon.repository.TransactionMapRepository;
import br.com.zenon.service.FraudAnalyzer;
import br.com.zenon.vo.IngestionResult;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App {

    private static final String FILE_PATH = "data/PS_20174392719_1491204439457_log.csv";
    private static final String BAD_DATA_FILE_PATH = "data/paysim_with_bad_data.csv";
    private static final int MAX_LINES = 50000;

    public static void main( String[] args ) {

        Transaction transaction1 = getTransaction1();
        System.out.println("Transação 1: " + transaction1);

        Transaction transaction2 = getTransaction2();
        System.out.println("Transação 2: " + transaction2);

        // TODO NTM - TAREFA/04: ESTUDAR BiConsumer PARA ENTENDER SE ENCAIXA BEM AQUI
        TransactionIngestor transactionIngestor = new TransactionIngestor();
        IngestionResult ingestionResult = transactionIngestor.ingest(FILE_PATH, MAX_LINES);

        ingestionResult.errors().forEach(System.err::println);
        System.out.println(ingestionResult.transactions().size());
        ingestionResult.transactions().forEach(System.out::println);

        List<Transaction> transactions = ingestionResult.transactions();
        FraudAnalyzer analyzer = new FraudAnalyzer();

        System.out.println("1. Total de Frandes: " + analyzer.countFrauds(transactions));
        System.out.println("2. Top 3 fraudes de maior valor: ");
        analyzer.topThreeFraudAmounts(transactions)
                .forEach(amount -> System.out.println(format(amount)));

        System.out.println("3. Clientes suspeitos: ");
        analyzer.topFiveFraudCustomers(transactions)
                .forEach(System.out::println);

        System.out.println("4. Prejuízo Total: " + format(analyzer.totalFraudAmount(transactions)));

        System.out.println("5. Fraudes por Tipo: ");
        analyzer.countFraudsByTransactionType(transactions)
                .forEach((type, count) ->
                        System.out.println("- " + type + ": " + count));

        TransactionListRepository repository = new TransactionListRepository();
        TransactionMapRepository repositoryMap = new TransactionMapRepository();
        String client1 = "C1231006815";
        String client2 = "C12345";
        String client3 = "C1868032458";

        repository.findTransactionByOriginCustomerName(client1)
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Transação não encontrada para o cliente " + client1));

        repository.findTransactionByOriginCustomerName(client2)
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Transação não encontrada para o cliente " + client2));

        long startList = System.nanoTime();
        Optional<Transaction> resultList = repository.findTransactionByOriginCustomerName(client3);
        long elapsedLList = System.nanoTime() - startList;
        System.out.println("Tempo de busca: " + elapsedLList + " ns (" + elapsedLList / 1_000_000.0 + " ms)");

        long startMap = System.nanoTime();
        Optional<Transaction> resultMap = repositoryMap.findTransactionByOriginCustomerName(client3);
        long elapsedMap = System.nanoTime() - startMap;
        System.out.println("Tempo de busca: " + elapsedMap + " ns (" + elapsedMap / 1_000_000.0 + " ms)");


    }

    private static Transaction getTransaction1() {
        TransactionCustomer origin1 = new TransactionCustomer(
                "C1231006815",
                new BigDecimal("170136.0"),
                new BigDecimal("160296.36")
        );

        TransactionCustomer recipient1 = new TransactionCustomer(
                "M1979787155",
                new BigDecimal("0.0"),
                new BigDecimal("0.0")
        );
        return new Transaction(
                1,
                TransactionType.PAYMENT,
                new BigDecimal("9839.64"),
                origin1,
                recipient1,
                false,
                false);
    }

    private static Transaction getTransaction2() {
        TransactionCustomer origin2 = new TransactionCustomer(
                "C1280323807",
                new BigDecimal("850002.52"),
                new BigDecimal("0.0")
        );
        TransactionCustomer recipient2 = new TransactionCustomer(
                "C873221189",
                new BigDecimal("6510099.11"),
                new BigDecimal("7360101.63")
        );
        return new Transaction(
                743,
                TransactionType.CASH_OUT,
                new BigDecimal("850002.52"),
                origin2,
                recipient2,
                true,
                false);
    }

    private static String format(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP).toString();
    }

}
