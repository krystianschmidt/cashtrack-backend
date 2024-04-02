package de.dhbw.cleanproject.domain.user.report;

import de.dhbw.cleanproject.domain.category.Category;
import de.dhbw.cleanproject.domain.transaction.Transaction;
import de.dhbw.cleanproject.domain.transaction.TransactionType;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportGenerator {
    public static Report generateReport(List<Transaction> allTransactions, YearMonth yearMonth) {
        Double totalIncome = getTotalIncome(allTransactions);
        Double totalExpense = getTotalExpense(allTransactions);

        BiggestCategory biggestExpenseCategory = findBiggestCategory(allTransactions, TransactionType.EXPENSE);
        BiggestCategory biggestIncomeCategory = findBiggestCategory(allTransactions, TransactionType.INCOME);

        List<Category> categoriesOverBudget = findCategoriesOverBudget(allTransactions);

        return Report.builder()
                .yearMonth(yearMonth)
                .totalExpense(totalExpense)
                .totalIncome(totalIncome)
                .biggestExpenseCategory(biggestExpenseCategory)
                .biggestIncomeCategory(biggestIncomeCategory)
                .categoriesOverBudget(categoriesOverBudget)
                .build();
    }

    private static Double getTotalIncome(List<Transaction> allTransactions) {
        return allTransactions.stream()
                .filter(transaction -> transaction.getType().equals(TransactionType.INCOME))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    private static Double getTotalExpense(List<Transaction> allTransactions) {
        return allTransactions.stream()
                .filter(transaction -> transaction.getType().equals(TransactionType.EXPENSE))
                .mapToDouble(transaction -> Math.abs(transaction.getAmount()))
                .sum();
    }

    private static BiggestCategory findBiggestCategory(List<Transaction> transactions, TransactionType type) {
        return transactions.stream()
                .filter(transaction -> transaction.getType() == type)
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.summingDouble(transaction -> Math.abs(transaction.getAmount())))
                )
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> BiggestCategory.builder()
                        .category(entry.getKey())
                        .amount(entry.getValue())
                        .type(type)
                        .build())
                .orElse(null);
    }

    public static List<Category> findCategoriesOverBudget(List<Transaction> transactions) {
        return transactions.stream()
                .map(Transaction::getCategory)
                .distinct()
                .filter(category -> category.getBudget() != null)
                .filter(category -> category.getBudget().getIsExceeded())
                .collect(Collectors.toList());
    }
}
