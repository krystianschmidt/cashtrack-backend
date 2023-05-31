package de.dhbw.cleanproject.domain.user;

import de.dhbw.cleanproject.domain.category.Category;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.YearMonth;

public interface UserApplication extends UserDetailsService {

    AppUser findByUsername(String username);

    boolean existsByUsername(String username);

    void createUser(AppUser registerUser);

    AppUser getUser();

    void save(AppUser user);


    void addCategory(Category category);


    void generateReport(YearMonth yearMonth);

}
