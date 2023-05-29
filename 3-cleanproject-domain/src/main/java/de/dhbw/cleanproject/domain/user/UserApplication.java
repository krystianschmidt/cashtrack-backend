package de.dhbw.cleanproject.domain.user;

import de.dhbw.cleanproject.domain.category.Category;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserApplication extends UserDetailsService {

    User findByUsername(String username);

    boolean existsByUsername(String username);


    void createUser(User registerUser);

    User getUser();


    void addCategory(Category category);

    void save(User user);

}
