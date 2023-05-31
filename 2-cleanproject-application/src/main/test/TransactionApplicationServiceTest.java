
import de.dhbw.cleanproject.application.transaction.TransactionApplicationService;
import de.dhbw.cleanproject.domain.category.Category;
import de.dhbw.cleanproject.domain.category.CategoryApplication;
import de.dhbw.cleanproject.domain.transaction.Transaction;
import de.dhbw.cleanproject.domain.transaction.TransactionRepository;
import de.dhbw.cleanproject.domain.transaction.TransactionType;
import de.dhbw.cleanproject.domain.user.AppUser;
import de.dhbw.cleanproject.domain.user.UserApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TransactionApplicationServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserApplication userApplication;

    @Mock
    private CategoryApplication categoryApplication;

    @InjectMocks
    private TransactionApplicationService transactionApplicationService;

    private AppUser user;
    private Authentication auth;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Mock SecurityContextHolder
        user = createTestUser();
        auth = new TestingAuthenticationToken(user, null);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void addTransactionTest() {
        // Create a new Transaction and Category instances for testing
        Transaction transaction = createTestTransaction();
        Category category = createTestCategory();
        transaction.setCategory(category);

        // Define the return value of categoryApplication.getCategory and transactionRepository.addTransaction when called with any UUID
        when(categoryApplication.getCategory(any(UUID.class))).thenReturn(category);
        when(transactionRepository.addTransaction(any(Transaction.class))).thenReturn(transaction);

        // Call the method under test
        transactionApplicationService.addTransaction(transaction);

        // Verify that transactionRepository.addTransaction was called with any Transaction instance
        verify(transactionRepository).addTransaction(any(Transaction.class));

        // Verify that categoryApplication.refreshBudget was called with any Category instance
        verify(categoryApplication).refreshBudget(any(Category.class));

        // Verify that userApplication.generateReport was called with any YearMonth instance
        verify(userApplication).generateReport(any(YearMonth.class));
    }


    @Test
    public void removeTransactionTest() {
        // Create a new Transaction and Category instances for testing
        Transaction transaction = createTestTransaction();
        Category category = createTestCategory();

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        category.setTransactions(transactions);
        transaction.setCategory(category);

        // Define the return value of transactionRepository.getTransaction when called with any UUID
        when(transactionRepository.getTransaction(any(UUID.class))).thenReturn(transaction);

        // Call the method under test
        transactionApplicationService.removeTransaction(transaction.getId());

        // Verify that transactionRepository.removeTransaction was called with any UUID
        verify(transactionRepository).removeTransaction(any(UUID.class));

        // Verify that categoryApplication.refreshBudget was called with any Category instance
        verify(categoryApplication).refreshBudget(any(Category.class));

        // Verify that userApplication.generateReport was called with any YearMonth instance
        verify(userApplication).generateReport(any(YearMonth.class));
    }

    private Transaction createTestTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setAmount(100.0);
        transaction.setType(TransactionType.INCOME);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setDescription("Test Transaction");
        transaction.setUser(user);
        return transaction;
    }

    private Category createTestCategory() {
        Category category = new Category();
        category.setId(UUID.randomUUID());
        return category;
    }

    private AppUser createTestUser() {
        AppUser user = new AppUser();
        user.setId(UUID.randomUUID());
        return user;
    }
}
