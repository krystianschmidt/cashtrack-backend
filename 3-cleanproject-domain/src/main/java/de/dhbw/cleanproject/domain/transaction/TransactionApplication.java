package de.dhbw.cleanproject.domain.transaction;

import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public interface TransactionApplication {

    void addTransaction(Transaction transaction);

    void removeTransaction(UUID transactionId);

    void updateTransaction(Transaction transaction);

    List<Transaction> getAllTransactions(YearMonth yearMonth);
}
