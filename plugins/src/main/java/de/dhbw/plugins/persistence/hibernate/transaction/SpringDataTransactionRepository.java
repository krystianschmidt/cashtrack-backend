package de.dhbw.plugins.persistence.hibernate.transaction;

import de.dhbw.cleanproject.domain.transaction.Transaction;
import de.dhbw.cleanproject.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.UUID;

public interface SpringDataTransactionRepository extends JpaRepository<Transaction, UUID> {

    //List<Transaction> findAllByUserAndTimestampMonthAndTimestampYear(User user, Month timestamp_month, Integer timestamp_year);
        List<Transaction> findByUserAndTimestampBetween(User user, LocalDateTime start, LocalDateTime end);

}
