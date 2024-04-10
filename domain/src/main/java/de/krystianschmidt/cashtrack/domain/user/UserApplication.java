package de.krystianschmidt.cashtrack.domain.user;

import de.krystianschmidt.cashtrack.domain.category.Category;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.YearMonth;

public interface UserApplication extends UserDetailsService {

    User findByUsername(String username);

    boolean existsByUsername(String username);

    void createUser(User registerUser);

    User getUser();

    void save(User user);


    void addCategory(Category category);


    void generateReport(YearMonth yearMonth);

}
