package br.com.zenon;

import br.com.zenon.domain.Transaction;
import br.com.zenon.domain.TransactionCustomer;
import br.com.zenon.domain.TransactionType;
import br.com.zenon.ingest.TransactionIngestor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {

    private static final String FILE_PATH = "data/PS_20174392719_1491204439457_log.csv";

    public static void main( String[] args ) {

        Transaction transaction1 = getTransaction1();
        System.out.println("Transação 1: " + transaction1);

        Transaction transaction2 = getTransaction2();
        System.out.println("Transação 2: " + transaction2);


        TransactionIngestor transactionIngestor = new TransactionIngestor();
        List<Transaction> transactionCustomers = transactionIngestor.ingest(FILE_PATH);
        if (transactionCustomers != null) {
            for (int i = 0; i < 10; i ++) {
                System.out.println(transactionCustomers.get(i));
            }
        } else {
            throw new RuntimeException("Erro ao ingetar/ler transações");
        }
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
