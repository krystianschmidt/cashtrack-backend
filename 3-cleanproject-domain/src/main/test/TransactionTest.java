import de.dhbw.cleanproject.domain.transaction.Transaction;
import de.dhbw.cleanproject.domain.transaction.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TransactionTest {

    @InjectMocks
    private Transaction transaction;

    private static final Logger LOGGER = Logger.getLogger(UserTest.class.getName());

    @Test
    void setAmountTest(){
        // Prepare data
        Double amount = 100.0;

        // Set amount
        transaction.setAmount(amount);

        // Check if amount is set
        assertEquals(amount, transaction.getAmount());
    }

    @Test
    void transactionBuilderTest(){
        // Prepare data
        Double amount = 100.0;

        // Build transaction
        Transaction transaction = Transaction.builder()
                .amount(amount)
                .build();

        // Check if transaction is built
        assertEquals(amount, transaction.getAmount());
    }

    @Test
    void setTypeTest(){
        // Prepare data
        Double income = 100.0;
        Double expense = -100.0;
        Double free = 0.0;

        // Set type
        Transaction transaction_expense = Transaction.builder()
                .amount(expense)
                .build();

        Transaction transaction_income = Transaction.builder()
                .amount(income)
                .build();

        Transaction transaction_free = Transaction.builder()
                .amount(free)
                .build();

        // Check if type is set
        assertEquals(TransactionType.EXPENSE, transaction_expense.getType());
        assertEquals(TransactionType.INCOME, transaction_income.getType());
        assertEquals(TransactionType.FREE, transaction_free.getType());
    }


}
