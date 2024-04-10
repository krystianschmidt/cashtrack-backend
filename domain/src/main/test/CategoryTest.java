import de.krystianschmidt.cashtrack.domain.category.Category;
import de.krystianschmidt.cashtrack.domain.transaction.Transaction;
import de.krystianschmidt.cashtrack.domain.transaction.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

public class CategoryTest {

    @InjectMocks
    private Category category;

    private static final Logger LOGGER = Logger.getLogger(UserTest.class.getName());

    @Test
    void addTransactionTest(){
        // Prepare data
        Transaction transaction1 = Transaction.builder()
                .id(UUID.randomUUID())
                .amount(100.0)
                .type(TransactionType.EXPENSE)
                .build();

        Transaction transaction2 = Transaction.builder()
                .id(UUID.randomUUID())
                .amount(0.0)
                .type(TransactionType.FREE)
                .build();

        Transaction transaction3 = Transaction.builder()
                .id(UUID.randomUUID())
                .amount(2000.0)
                .type(TransactionType.INCOME)
                .build();

        // Add transactions
        category.addTransaction(transaction1);
        category.addTransaction(transaction2);
        category.addTransaction(transaction3);

        // Check if transactions are added
        assertEquals(3, category.getTransactions().size());
        assertTrue(category.getTransactions().contains(transaction1));
        assertTrue(category.getTransactions().contains(transaction2));
        assertTrue(category.getTransactions().contains(transaction3));
    }

    @Test
    void removeTransactionTest(){
        // Prepare data
        Transaction transaction1 = Transaction.builder()
                .id(UUID.randomUUID())
                .amount(-100.0)
                .type(TransactionType.EXPENSE)
                .build();

        Transaction transaction2 = Transaction.builder()
                .id(UUID.randomUUID())
                .amount(1000.0)
                .type(TransactionType.EXPENSE)
                .build();

        Transaction transaction3 = Transaction.builder()
                .id(UUID.randomUUID())
                .amount(2000.0)
                .type(TransactionType.INCOME)
                .build();

        // Add transactions
        category.addTransaction(transaction1);
        category.addTransaction(transaction2);
        category.addTransaction(transaction3);

        // Check if transactions are added
        assertEquals(3, category.getTransactions().size());
        assertTrue(category.getTransactions().contains(transaction1));
        assertTrue(category.getTransactions().contains(transaction2));
        assertTrue(category.getTransactions().contains(transaction3));

        // Remove transactions
        category.removeTransaction(transaction1);
        category.removeTransaction(transaction2);
        category.removeTransaction(transaction3);

        // Check if transactions are removed
        assertEquals(0, category.getTransactions().size());
        assertFalse(category.getTransactions().contains(transaction1));
        assertFalse(category.getTransactions().contains(transaction2));
        assertFalse(category.getTransactions().contains(transaction3));
    }

    @Test
    void setCategoryTest(){
        // Prepare data
        category = Category.builder()
                .id(UUID.randomUUID())
                .name("Groceries")
                .build();

        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID())
                .amount(100.0)
                .type(TransactionType.INCOME)
                .build();

        transaction.setCategory(category);

        // Check if category is set
        assertEquals(category, transaction.getCategory());
    }


}
