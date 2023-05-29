package de.dhbw.cleanproject.adapter.category.budget;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BudgetResource {
    private final Double amount, usedAmount;
    private final Boolean isExceeded;
}
