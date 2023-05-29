package de.dhbw.cleanproject.adapter.category.budget;

import de.dhbw.cleanproject.domain.category.Budget;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BudgetToBudgetResourceMapper implements Function<Budget, BudgetResource> {
    @Override
    public BudgetResource apply(Budget budget) {
        if(budget == null)
            return null;

        return BudgetResource.builder()
                .amount(budget.getAmount())
                .usedAmount(budget.getUsedAmount())
                .isExceeded(budget.getIsExceeded())
                .build();
    }
}
