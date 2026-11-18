package br.com.zenon;

import br.com.zenon.domain.Transaction;
import br.com.zenon.domain.TransactionCustomer;
import br.com.zenon.domain.TransactionType;
import br.com.zenon.ingest.TransactionIngestor;
import br.com.zenon.vo.IngestionResult;

import java.math.BigDecimal;

/**
 * Hello world!
 *
 */
public class App {

    private static final String FILE_PATH = "data/PS_20174392719_1491204439457_log.csv";
    private static final String BAD_DATA_FILE_PATH = "data/paysim_with_bad_data.csv";

    public static void main( String[] args ) {

        Transaction transaction1 = getTransaction1();
        System.out.println("Transação 1: " + transaction1);

        Transaction transaction2 = getTransaction2();
        System.out.println("Transação 2: " + transaction2);

        // TODO NTM - TAREFA/04: ESTUDAR BiConsumer PARA ENTENDER SE ENCAIXA BEM AQUI
        TransactionIngestor transactionIngestor = new TransactionIngestor();
        IngestionResult ingestionResult = transactionIngestor.ingest(BAD_DATA_FILE_PATH);

        ingestionResult.errors().forEach(System.err::println);
        System.out.println(ingestionResult.transactions().size());
        ingestionResult.transactions().forEach(System.out::println);

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

}
