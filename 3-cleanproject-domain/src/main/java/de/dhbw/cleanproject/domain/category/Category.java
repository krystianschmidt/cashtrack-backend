package de.dhbw.cleanproject.domain.category;

import de.dhbw.cleanproject.domain.transaction.Transaction;
import lombok.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
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

    private Budget budget;
}
