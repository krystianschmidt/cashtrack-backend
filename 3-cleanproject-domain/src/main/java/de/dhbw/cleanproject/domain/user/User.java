package de.dhbw.cleanproject.domain.user;

import de.dhbw.cleanproject.domain.category.Category;
import de.dhbw.cleanproject.domain.transaction.Transaction;
import de.dhbw.cleanproject.domain.transaction.TransactionApplication;
import de.dhbw.cleanproject.domain.transaction.TransactionType;
import de.dhbw.cleanproject.domain.user.report.BiggestCategory;
import de.dhbw.cleanproject.domain.user.report.Report;
import lombok.*;
import lombok.experimental.PackagePrivate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class User implements UserDetails {

    @Id
    @Type(type="uuid-char")
    @Column(nullable = false)
    @GenericGenerator(name = "client_id", strategy = "de.dhbw.cleanproject.abstractioncode.JpaIdGenerator")
    @GeneratedValue(generator = "client_id")
    private UUID id;

    @Column(unique=true)
    private String username;

    // The user's password
    private String password;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Category> categories;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Report> reports;

    public void setName(String name) {
        this.name = name.trim();
    }

    public void setUsername(String username) {
        this.username = username.trim();
    }


    /* --------------------------------- */
    /* UserDetails interface methods */

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public void addCategory(Category category) {
        if (categories.stream().anyMatch(c -> c.getId().equals(category.getId())))
            throw new RuntimeException("Category already exists!");
        categories.add(category);
    }

    public void generateReport(List<Transaction> allTransactions, YearMonth yearMonth) {
        Double totalIncome = 0.;
        Double totalExpense = 0.;
        for (Transaction transaction : allTransactions) {
            if(transaction.getType().equals(TransactionType.INCOME)){
                totalIncome += transaction.getAmount();
            }else{
                totalExpense += Math.abs(transaction.getAmount());
            }
        }

        BiggestCategory biggestExpenseCategory = findBiggestCategory(allTransactions, TransactionType.EXPENSE);

        BiggestCategory biggestIncomeCategory = findBiggestCategory(allTransactions, TransactionType.INCOME);

        List<Category> categoriesOverBudget = findCategoriesOverBudget(allTransactions);

        Report report = Report.builder()
                .yearMonth(yearMonth)
                .totalExpense(totalExpense)
                .totalIncome(totalIncome)
                .biggestExpenseCategory(biggestExpenseCategory)
                .biggestIncomeCategory(biggestIncomeCategory)
                .categoriesOverBudget(categoriesOverBudget)
                .build();

        reports.remove(report);
        reports.add(report);
    }

    private List<Category> findCategoriesOverBudget(List<Transaction> transactions) {
        return categories.stream()
                .filter(category -> category.getBudget() != null)
                .filter(category -> category.getBudget().getIsExceeded())
                .collect(Collectors.toList());
    }


    private BiggestCategory findBiggestCategory(List<Transaction> transactions, TransactionType type) {
        return transactions.stream()
                .filter(transaction -> transaction.getType() == type)
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.summingDouble(transaction -> Math.abs(transaction.getAmount())))
                )
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> BiggestCategory.builder()
                        .category(entry.getKey())
                        .amount(entry.getValue())
                        .type(type)
                        .build())
                .orElse(null);
    }

}

