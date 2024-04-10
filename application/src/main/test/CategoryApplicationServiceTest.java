import de.krystianschmidt.cashtrack.application.category.CategoryApplicationService;
import de.krystianschmidt.cashtrack.domain.category.Budget;
import de.krystianschmidt.cashtrack.domain.category.Category;
import de.krystianschmidt.cashtrack.domain.category.CategoryRepository;
import de.krystianschmidt.cashtrack.domain.transaction.Transaction;
import de.krystianschmidt.cashtrack.domain.transaction.TransactionType;
import de.krystianschmidt.cashtrack.domain.user.User;
import de.krystianschmidt.cashtrack.domain.user.UserApplication;
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
import java.util.UUID;

import static org.mockito.Mockito.*;

public class CategoryApplicationServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserApplication userApplication;

    @InjectMocks
    private CategoryApplicationService categoryApplicationService;

    private User user;
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
    public void addCategoryTest() {
        Category category = createTestCategory();
        categoryApplicationService.addCategory(category);
        verify(userApplication).addCategory(category);
    }

    @Test
    public void removeCategoryTest() {
        Category category = createTestCategory();
        user.getCategories().add(category);

        when(userApplication.getUser()).thenReturn(user);

        categoryApplicationService.removeCategory(category.getId());

        verify(userApplication).save(any(User.class));
    }

    @Test
    public void addBudgetTest() {
        Category category = createTestCategory();
        user.getCategories().add(category);
        Budget budget = new Budget(100.0, 0.0, false);

        when(userApplication.getUser()).thenReturn(user);

        categoryApplicationService.addBudget(category.getId(), budget);

        verify(categoryRepository).saveCategory(any(Category.class));
        verify(userApplication).generateReport(any(YearMonth.class));
    }

    @Test
    public void refreshBudgetTest() {
        Category category = createTestCategory();
        Transaction transaction = createTestTransaction();
        category.getTransactions().add(transaction);

        when(categoryRepository.getCategory(any(UUID.class))).thenReturn(category);

        categoryApplicationService.refreshBudget(category);

        verify(categoryRepository).saveCategory(any(Category.class));
    }

    private Transaction createTestTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setAmount(100.0);
        transaction.setType(TransactionType.EXPENSE);
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

    private User createTestUser() {
        User user = new User();
        user.setId(UUID.randomUUID());
        return user;
    }
}
