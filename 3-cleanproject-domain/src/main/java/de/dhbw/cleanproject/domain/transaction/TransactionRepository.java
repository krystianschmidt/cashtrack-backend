package de.dhbw.cleanproject.domain.transaction;

import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository {

    Transaction addTransaction(Transaction transaction);

    void removeTransaction(UUID transactionId);

    void updateTransaction(Transaction transaction);

    List<Transaction> getAllTransactions(YearMonth yearMonth);

    Transaction getTransaction(UUID transactionId);

}
