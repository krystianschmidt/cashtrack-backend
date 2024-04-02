package de.dhbw.cleanproject.adapter.user.report;

import de.dhbw.cleanproject.adapter.category.CategoryResource;
import de.dhbw.cleanproject.adapter.category.CategoryToCategoryResourceMapper;
import de.dhbw.cleanproject.domain.category.Category;
import de.dhbw.cleanproject.domain.user.report.BiggestCategory;
import de.dhbw.cleanproject.domain.user.report.Report;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ReportToReportResourceMapper implements Function<Report, ReportResource> {

    private final BiggestCategoryToBiggestResourceMapper biggestCategoryToBiggestResourceMapper;
    private final CategoryToCategoryResourceMapper categoryToCategoryResourceMapper;

    @Override
    public ReportResource apply(Report report) {

        List<CategoryResource> categoriesOverBudget = report.getCategoriesOverBudget().stream()
                .map(categoryToCategoryResourceMapper)
                .collect(java.util.stream.Collectors.toList());

        return ReportResource.builder()
                .biggestExpenseCategory(
                        biggestCategoryToBiggestResourceMapper.apply(report.getBiggestExpenseCategory())
                )
                .biggestIncomeCategory(
                        biggestCategoryToBiggestResourceMapper.apply(report.getBiggestIncomeCategory())
                )
                .categoriesOverBudget(categoriesOverBudget)
                .totalExpense(report.getTotalExpense())
                .totalIncome(report.getTotalIncome())
                .yearMonth(report.getYearMonth())
                .build();
    }
}
