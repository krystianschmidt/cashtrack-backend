package de.dhbw.cleanproject.application.transaction;

import de.dhbw.cleanproject.domain.transaction.Transaction;
import de.dhbw.cleanproject.domain.transaction.TransactionApplication;
import de.dhbw.cleanproject.domain.transaction.TransactionRepository;
import de.dhbw.cleanproject.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionApplicationService implements TransactionApplication {

    private final TransactionRepository transactionRepository;

    @Override
    public void addTransaction(Transaction transaction) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        transaction.setUser(user);

        transactionRepository.addTransaction(transaction);
    }

    @Override
    public void removeTransaction(UUID transactionId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Transaction transaction = transactionRepository.getTransaction(transactionId);
        if(!transaction.getUser().getId().equals(user.getId()))
            throw new RuntimeException("Transaction does not belong to user");
        transactionRepository.removeTransaction(transactionId);
    }

    @Override
    public void updateTransaction(Transaction transaction) {

    }

    @Override
    public List<Transaction> getAllTransactions(Integer month, Integer year) {
        return transactionRepository.getAllTransactions(month, year);
    }


}
