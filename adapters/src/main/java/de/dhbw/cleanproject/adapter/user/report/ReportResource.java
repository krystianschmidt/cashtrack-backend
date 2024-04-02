package de.dhbw.cleanproject.adapter.user.report;


import de.dhbw.cleanproject.adapter.category.CategoryResource;
import de.dhbw.cleanproject.domain.category.Category;
import de.dhbw.cleanproject.domain.user.report.BiggestCategory;
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
