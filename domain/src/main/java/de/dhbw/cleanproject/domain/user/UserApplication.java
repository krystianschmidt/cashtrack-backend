package de.dhbw.cleanproject.domain.user;

import de.dhbw.cleanproject.domain.category.Category;
import de.dhbw.cleanproject.domain.user.report.Report;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

public interface UserApplication extends UserDetailsService {

    User findByUsername(String username);

    boolean existsByUsername(String username);

    void createUser(User registerUser);

    User getUser();

    void save(User user);


    void addCategory(Category category);


    void generateReport(YearMonth yearMonth);

}
