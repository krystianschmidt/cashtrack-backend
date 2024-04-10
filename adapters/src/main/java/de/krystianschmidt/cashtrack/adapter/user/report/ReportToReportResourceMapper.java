package de.krystianschmidt.cashtrack.adapter.user.report;

import de.krystianschmidt.cashtrack.adapter.category.CategoryResource;
import de.krystianschmidt.cashtrack.adapter.category.CategoryToCategoryResourceMapper;
import de.krystianschmidt.cashtrack.domain.user.report.Report;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
