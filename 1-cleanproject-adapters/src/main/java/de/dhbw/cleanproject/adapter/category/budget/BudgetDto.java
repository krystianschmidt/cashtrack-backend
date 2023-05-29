package de.dhbw.cleanproject.adapter.category.budget;

import lombok.Data;

@Data
public class BudgetDto {
    private final Double amount, usedAmount;
    private final Boolean isExceeded;
}
