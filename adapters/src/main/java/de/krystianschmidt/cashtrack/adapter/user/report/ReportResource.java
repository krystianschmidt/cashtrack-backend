package de.krystianschmidt.cashtrack.adapter.user.report;


import de.krystianschmidt.cashtrack.adapter.category.CategoryResource;
import lombok.Builder;
import lombok.Getter;

import java.time.YearMonth;
import java.util.List;

@Getter
@Builder
public class ReportResource {
    private final YearMonth yearMonth;

    private final Double totalIncome;

    private final Double totalExpense;

    private final BiggestCategoryResource biggestExpenseCategory;

    private final BiggestCategoryResource biggestIncomeCategory;

    private final List<CategoryResource> categoriesOverBudget;
}
