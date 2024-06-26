package de.krystianschmidt.cashtrack.domain.user.report;

import de.krystianschmidt.cashtrack.domain.category.Category;
import de.krystianschmidt.cashtrack.domain.transaction.TransactionType;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
public class BiggestCategory {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Category category;

    private Double amount;

    private TransactionType type;

    @Override
    public String toString() {
        return "BiggestCategory{" +
                "id=" + id +
                ", category=" + category +
                ", amount=" + amount +
                ", type=" + type +
                '}';
    }
}
