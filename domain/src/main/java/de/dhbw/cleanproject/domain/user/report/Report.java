package de.dhbw.cleanproject.domain.user.report;

import de.dhbw.cleanproject.domain.category.Category;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Builder
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
public class Report {

    @Id
    @Type(type="uuid-char")
    @Column(nullable = false)
    @GenericGenerator(name = "client_id", strategy = "de.dhbw.cleanproject.abstractioncode.JpaIdGenerator")
    @GeneratedValue(generator = "client_id")
    private UUID id;

    private YearMonth yearMonth;

    private Double totalIncome;

    private Double totalExpense;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    private BiggestCategory biggestExpenseCategory;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    private BiggestCategory biggestIncomeCategory;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Category> categoriesOverBudget;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        // add all fields which identify a category
        return Objects.equals(yearMonth, report.yearMonth);
    }

    @Override
    public int hashCode() {
        // add all fields which identify a category
        return Objects.hash(yearMonth);
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", yearMonth=" + yearMonth +
                ", totalIncome=" + totalIncome +
                ", totalExpense=" + totalExpense +
                ", biggestExpenseCategory=" + biggestExpenseCategory +
                ", biggestIncomeCategory=" + biggestIncomeCategory +
                ", categoriesOverBudget=" + categoriesOverBudget +
                '}';
    }
}
