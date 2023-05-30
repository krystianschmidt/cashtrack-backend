package de.dhbw.cleanproject.domain.user;

import de.dhbw.cleanproject.domain.category.Category;
import de.dhbw.cleanproject.domain.transaction.Transaction;
import de.dhbw.cleanproject.domain.transaction.TransactionApplication;
import de.dhbw.cleanproject.domain.transaction.TransactionType;
import de.dhbw.cleanproject.domain.user.report.BiggestCategory;
import de.dhbw.cleanproject.domain.user.report.Report;
import de.dhbw.cleanproject.domain.user.report.ReportGenerator;
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

        Report report = ReportGenerator.generateReport(allTransactions, yearMonth);

        reports.remove(report);
        reports.add(report);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", categories=" + categories +
                ", reports=" + reports +
                '}';
    }
}

