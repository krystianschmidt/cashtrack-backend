package de.dhbw.cleanproject.domain.transaction;

import de.dhbw.cleanproject.domain.category.Category;
import de.dhbw.cleanproject.domain.user.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Transaction {

    @Id
    @Type(type="uuid-char")
    @Column(nullable = false)
    @GenericGenerator(name = "client_id", strategy = "de.dhbw.cleanproject.abstractioncode.JpaIdGenerator")
    @GeneratedValue(generator = "client_id")
    private UUID id;

    private Double amount;

    private LocalDateTime timestamp;

    private TransactionType type;

    private String description;

    @ManyToOne
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;


    public void setAmount(Double amount){
        this.amount = amount;
        if(amount == 0)
            this.type = TransactionType.FREE;
        else
            this.type = amount > 0 ? TransactionType.INCOME : TransactionType.EXPENSE;
    }

    public static class TransactionBuilder{
        public TransactionBuilder amount(Double amount){
            this.amount = amount;
            if(amount == 0)
                type = TransactionType.FREE;
            else
                type = amount > 0 ? TransactionType.INCOME : TransactionType.EXPENSE;
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction transaction = (Transaction) o;
        // add all fields which identify a category
        return Objects.equals(id, transaction.id);
    }

    @Override
    public int hashCode() {
        // add all fields which identify a category
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", category=" + category +
                '}';
    }
}
