package de.dhbw.plugins.persistence.hibernate.transaction;

import de.dhbw.cleanproject.domain.transaction.Transaction;
import de.dhbw.cleanproject.domain.transaction.TransactionRepository;
import de.dhbw.cleanproject.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TransactionRepositoryBridge implements TransactionRepository {

    private final SpringDataTransactionRepository repository;


    @Override
    public void addTransaction(Transaction transaction) {
        repository.save(transaction);
    }

    @Override
    public void removeTransaction(UUID transactionId) {
        repository.deleteById(transactionId);
    }

    @Override
    public void updateTransaction(Transaction transaction) {

    }

    @Override
    public List<Transaction> getAllTransactions(Integer month, Integer year) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        YearMonth yearMonth = YearMonth.of(year, month);
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
