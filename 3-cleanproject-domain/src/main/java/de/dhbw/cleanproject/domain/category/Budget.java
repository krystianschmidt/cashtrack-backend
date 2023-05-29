package de.dhbw.cleanproject.domain.category;

import lombok.*;

import javax.persistence.Embeddable;

@Builder
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Embeddable
public final class Budget {
    private final Double amount, usedAmount;
    private final Boolean isExceeded;
}
