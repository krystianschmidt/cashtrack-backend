import de.krystianschmidt.cashtrack.application.user.UserApplicationService;
import de.krystianschmidt.cashtrack.domain.category.Category;
import de.krystianschmidt.cashtrack.domain.transaction.TransactionRepository;
import de.krystianschmidt.cashtrack.domain.user.User;
import de.krystianschmidt.cashtrack.domain.user.UserRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class UserApplicationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserApplicationService userApplicationService;

    private User testUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        testUser = createTestUser();

        testUser.setCategories(new ArrayList<>());
        testUser.setReports(new HashSet<>());

        setAuthenticationContext(testUser);
    }

    @Test
    public void createUserTest() {
        User newUser = new User();
        newUser.setUsername("NewUser");
        newUser.setPassword("NewPassword");

        when(userRepository.existsByUsername(newUser.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(newUser.getPassword())).thenReturn("EncodedPassword");

        userApplicationService.createUser(newUser);

        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    public void getUserTest() {
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(testUser);

        User foundUser = userApplicationService.getUser();

        assert foundUser.getUsername().equals(testUser.getUsername());
    }

    @Test
    public void addCategoryTest() {
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(testUser);
        Category category = new Category();
        category.setId(UUID.randomUUID());

        userApplicationService.addCategory(category);

        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    public void generateReportTest() {
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(testUser);
        YearMonth yearMonth = YearMonth.now();

        userApplicationService.generateReport(yearMonth);

        verify(transactionRepository, times(1)).getAllTransactions(yearMonth);
        verify(userRepository, times(1)).save(testUser);
    }

    private User createTestUser() {
        User user = new User();
        user.setUsername("TestUser");
        user.setPassword("TestPassword");
        return user;
    }

    private void setAuthenticationContext(User user) {
        Authentication auth = new TestingAuthenticationToken(user, null);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
    }
}
