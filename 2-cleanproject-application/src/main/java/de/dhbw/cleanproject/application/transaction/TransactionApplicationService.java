package de.dhbw.cleanproject.application.transaction;

import de.dhbw.cleanproject.domain.category.Category;
import de.dhbw.cleanproject.domain.category.CategoryApplication;
import de.dhbw.cleanproject.domain.transaction.Transaction;
import de.dhbw.cleanproject.domain.transaction.TransactionApplication;
import de.dhbw.cleanproject.domain.transaction.TransactionRepository;
import de.dhbw.cleanproject.domain.user.AppUser;
import de.dhbw.cleanproject.domain.user.UserApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionApplicationService implements TransactionApplication {

    private final TransactionRepository transactionRepository;
    private final UserApplication userApplication;
    private final CategoryApplication categoryApplication;

    @Override
    public void addTransaction(Transaction transaction) {
        AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Category category = categoryApplication.getCategory(transaction.getCategory().getId());

        setCategoryAndUser(transaction, user, category);

        Transaction savedTransaction = transactionRepository.addTransaction(transaction);
        categoryApplication.refreshBudget(savedTransaction.getCategory());

        userApplication.generateReport(YearMonth.from(savedTransaction.getTimestamp()));
    }

    private static void setCategoryAndUser(Transaction transaction, AppUser user, Category category) {
        transaction.setUser(user);
        transaction.setCategory(category);
        transaction.getCategory().addTransaction(transaction);
    }

    @Override
    public void removeTransaction(UUID transactionId) {
        AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Transaction transaction = transactionRepository.getTransaction(transactionId);
        if(!transaction.getUser().getId().equals(user.getId()))
            throw new RuntimeException("Transaction does not belong to user");

        transaction.getCategory().removeTransaction(transaction);
        transactionRepository.removeTransaction(transactionId);

        categoryApplication.refreshBudget(transaction.getCategory());
        userApplication.generateReport(YearMonth.from(transaction.getTimestamp()));
    }

    @Override
    public void updateTransaction(Transaction transaction) {

        categoryApplication.refreshBudget(transaction.getCategory());
        userApplication.generateReport(YearMonth.from(transaction.getTimestamp()));
    }

    @Override
    public List<Transaction> getAllTransactions(YearMonth yearMonth) {
        return transactionRepository.getAllTransactions(yearMonth);
    }


}
