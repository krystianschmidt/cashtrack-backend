package de.dhbw.plugins.persistence.hibernate.transaction;

import de.dhbw.cleanproject.domain.transaction.Transaction;
import de.dhbw.cleanproject.domain.transaction.TransactionRepository;
import de.dhbw.cleanproject.domain.user.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TransactionRepositoryBridge implements TransactionRepository {

    private final SpringDataTransactionRepository repository;


    @Override
    public Transaction addTransaction(Transaction transaction) {
        return repository.save(transaction);
    }

    @Override
    public void removeTransaction(UUID transactionId) {
        repository.deleteById(transactionId);
    }

    @Override
    public void updateTransaction(Transaction transaction) {

    }

    @Override
    public List<Transaction> getAllTransactions(YearMonth yearMonth) {
        AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime end = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        return repository.findByUserAndTimestampBetween(user, start, end);
        //return repository.findAllByUserAndTimestampMonthAndTimestampYear(user, Month.of(month), year);
    }

    @Override
    public Transaction getTransaction(UUID transactionId) {
        return repository.findById(transactionId).orElse(null);
    }

}
