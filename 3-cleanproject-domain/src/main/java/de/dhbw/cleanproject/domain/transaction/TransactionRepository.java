package de.dhbw.cleanproject.domain.transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository {

    void addTransaction(Transaction transaction);

    void removeTransaction(UUID transactionId);

    void updateTransaction(Transaction transaction);

    List<Transaction> getAllTransactions(Integer month, Integer year);

    Transaction getTransaction(UUID transactionId);

}
