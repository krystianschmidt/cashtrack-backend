package de.krystianschmidt.cashtrack.application.transaction;

import de.krystianschmidt.cashtrack.domain.category.Category;
import de.krystianschmidt.cashtrack.domain.category.CategoryApplication;
import de.krystianschmidt.cashtrack.domain.transaction.Transaction;
import de.krystianschmidt.cashtrack.domain.transaction.TransactionApplication;
import de.krystianschmidt.cashtrack.domain.transaction.TransactionRepository;
import de.krystianschmidt.cashtrack.domain.user.User;
import de.krystianschmidt.cashtrack.domain.user.UserApplication;
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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Category category = categoryApplication.getCategory(transaction.getCategory().getId());

        setCategoryAndUser(transaction, user, category);

        Transaction savedTransaction = transactionRepository.addTransaction(transaction);
        categoryApplication.refreshBudget(savedTransaction.getCategory());

        userApplication.generateReport(YearMonth.from(savedTransaction.getTimestamp()));
    }

    private static void setCategoryAndUser(Transaction transaction, User user, Category category) {
        transaction.setUser(user);
        transaction.setCategory(category);
        transaction.getCategory().addTransaction(transaction);
    }

    @Override
    public void removeTransaction(UUID transactionId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
