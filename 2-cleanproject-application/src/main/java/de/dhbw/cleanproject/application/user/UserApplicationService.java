package de.dhbw.cleanproject.application.user;

import de.dhbw.cleanproject.domain.category.Category;
import de.dhbw.cleanproject.domain.transaction.Transaction;
import de.dhbw.cleanproject.domain.transaction.TransactionRepository;
import de.dhbw.cleanproject.domain.user.AppUser;
import de.dhbw.cleanproject.domain.user.UserApplication;
import de.dhbw.cleanproject.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserApplicationService implements UserApplication {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TransactionRepository transactionRepository;



    public AppUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public void createUser(AppUser registerUser) {
        if(userRepository.existsByUsername(registerUser.getUsername())){
            throw new RuntimeException("Username already exists!");
        }

        String encryptedPassword = passwordEncoder.encode(registerUser.getPassword());
        registerUser.setPassword(encryptedPassword);

        userRepository.save(registerUser);
    }

    @Override
    public AppUser getUser() {
        AppUser principal = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername());
    }

    @Override
    public void addCategory(Category category) {
        AppUser user = getUser();
        user.addCategory(category);
        userRepository.save(user);
    }

    @Override
    public void generateReport(YearMonth yearMonth) {
        List<Transaction> allTransactions = transactionRepository.getAllTransactions(yearMonth);
        AppUser user = getUser();
        user.generateReport(allTransactions, yearMonth);

        save(user);
    }

    @Override
    public void save(AppUser user) {
        userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }


}
