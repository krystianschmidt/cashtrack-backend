
import de.dhbw.cleanproject.domain.category.Budget;
import de.dhbw.cleanproject.domain.category.Category;
import de.dhbw.cleanproject.domain.transaction.Transaction;
import de.dhbw.cleanproject.domain.transaction.TransactionType;
import de.dhbw.cleanproject.domain.user.User;
import de.dhbw.cleanproject.domain.user.report.BiggestCategory;
import de.dhbw.cleanproject.domain.user.report.Report;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.YearMonth;
import java.util.*;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @InjectMocks
    private User user;

    private static final Logger LOGGER = Logger.getLogger(UserTest.class.getName());

    @Test
    void generateReportTest() {

        // Prepare data
        Category category1 = Category.builder()
                .id(UUID.randomUUID())
                .name("Groceries")
                .build();

        Category category2 = Category.builder()
                .id(UUID.randomUUID())
                .name("Rent")
                .build();

        Transaction transaction1 = Transaction.builder()
                .id(UUID.randomUUID())
                .amount(100.0)
                .category(category1)
                .type(TransactionType.EXPENSE)
                .build();

        Transaction transaction2 = Transaction.builder()
                .id(UUID.randomUUID())
                .amount(1000.0)
                .category(category2)
                .type(TransactionType.EXPENSE)
                .build();

        Transaction transaction3 = Transaction.builder()
                .id(UUID.randomUUID())
                .amount(2000.0)
                .category(category1)
                .type(TransactionType.INCOME)
                .build();

        // Initialize transactions
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);
        LOGGER.info("Transactions: " + transactions);

        // Initialize categories
        user.setCategories(new ArrayList<>(Arrays.asList(category1, category2)));
        LOGGER.info("Categories: " + user.getCategories());

        // Initialize reports
        user.setReports(new HashSet<>());

        // Call method under test
        user.generateReport(transactions, YearMonth.now());
        LOGGER.info("Reports: " + user.getReports());

        // Assert results
        assertEquals(1, user.getReports().size());
        Report report = user.getReports().iterator().next();
        assertNotNull(report);
        assertEquals(1100.0, report.getTotalExpense());
        assertEquals(2000.0, report.getTotalIncome());

        // Assert biggest categories
        BiggestCategory biggestExpenseCategory = report.getBiggestExpenseCategory();
        assertNotNull(biggestExpenseCategory);
        assertEquals("Rent", biggestExpenseCategory.getCategory().getName());
        assertEquals(1000.0, biggestExpenseCategory.getAmount());

        // Assert biggest categories
        BiggestCategory biggestIncomeCategory = report.getBiggestIncomeCategory();
        assertNotNull(biggestIncomeCategory);
        assertEquals("Groceries", biggestIncomeCategory.getCategory().getName());
        assertEquals(2000.0, biggestIncomeCategory.getAmount());
    }

    @Test
    void addCategoryTest() {
        // Prepare data
        Category category1 = Category.builder()
                .id(UUID.randomUUID())
                .name("Groceries")
                .build();
        LOGGER.info("Category: " + category1);

        // Initialize categories
        user.setCategories(new ArrayList<>());

        // Call method under test
        user.addCategory(category1);
        LOGGER.info("Categories: " + user.getCategories());

        // Assert results
        assertEquals(1, user.getCategories().size());
        assertTrue(user.getCategories().contains(category1));
    }

    @Test
    void addCategory_duplicateTest() {
        // Prepare data
        Category category1 = Category.builder()
                .id(UUID.randomUUID())
                .name("Groceries")
                .build();
        user.setCategories(new ArrayList<>(Collections.singletonList(category1)));

        // Assert that exception is thrown when trying to add a duplicate category
        assertThrows(RuntimeException.class, () -> user.addCategory(category1));
    }

    @Test
    void findCategoriesOverBudgetTest() {
        // Prepare data
        Budget exceededBudget = Budget.builder().amount(100.0).usedAmount(150.0).isExceeded(true).build();
        LOGGER.info("Budget: " + exceededBudget);
        Category category1 = Category.builder()
                .id(UUID.randomUUID())
                .name("Groceries")
                .budget(exceededBudget)
                .build();
        LOGGER.info("Category: " + category1);

        Budget notExceededBudget = Budget.builder().amount(200.0).usedAmount(150.0).isExceeded(false).build();
        LOGGER.info("Budget: " + notExceededBudget);
        Category category2 = Category.builder()
                .id(UUID.randomUUID())
                .name("Rent")
                .budget(notExceededBudget)
                .build();
        LOGGER.info("Category: " + category2);

        user.setCategories(new ArrayList<>(Arrays.asList(category1, category2)));
        LOGGER.info("Categories: " + user.getCategories());

        // Call method under test
        List<Category> categoriesOverBudget = user.findCategoriesOverBudget(new ArrayList<>());
        LOGGER.info("Categories over budget: " + categoriesOverBudget);

        // Assert results
        assertEquals(1, categoriesOverBudget.size());
        assertTrue(categoriesOverBudget.contains(category1));
    }
}
