package de.dhbw.cleanproject.domain.category;

import de.dhbw.cleanproject.domain.transaction.Transaction;
import de.dhbw.cleanproject.domain.transaction.TransactionType;
import lombok.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Category {

    @Id
    @Type(type="uuid-char")
    @Column(nullable = false)
    @GenericGenerator(name = "client_id", strategy = "de.dhbw.cleanproject.abstractioncode.JpaIdGenerator")
    @GeneratedValue(generator = "client_id")
    private UUID id;

    private String name;

    private String color;

    private String iconName;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    @Embedded
    private Budget budget;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        // add all fields which identify a category
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        // add all fields which identify a category
        return Objects.hash(id);
    }

    public void addTransaction(Transaction transaction) {
        if (transactions == null) {
            transactions = new ArrayList<>();
        }
        transactions.add(transaction);
        transaction.setCategory(this);
    }


    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction);
        transaction.setCategory(this);
    }

}
