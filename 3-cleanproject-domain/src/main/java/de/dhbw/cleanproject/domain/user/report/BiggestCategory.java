package de.dhbw.cleanproject.domain.user.report;

import de.dhbw.cleanproject.domain.category.Category;
import de.dhbw.cleanproject.domain.transaction.TransactionType;
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
}
