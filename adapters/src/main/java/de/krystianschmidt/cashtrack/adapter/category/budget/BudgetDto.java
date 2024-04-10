package de.krystianschmidt.cashtrack.adapter.category.budget;

import lombok.Data;

@Data
public class BudgetDto {
    private final Double amount, usedAmount;
    private final Boolean isExceeded;
}
