package de.dhbw.cleanproject.adapter.category.budget;

import de.dhbw.cleanproject.domain.category.Budget;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BudgetDtoToBudgetMapper implements Function<BudgetDto, Budget> {
    @Override
    public Budget apply(BudgetDto budgetDto) {
        return Budget.builder()
                .amount(budgetDto.getAmount())
                .usedAmount(budgetDto.getUsedAmount())
                .isExceeded(budgetDto.getIsExceeded())
                .build();
    }
}
