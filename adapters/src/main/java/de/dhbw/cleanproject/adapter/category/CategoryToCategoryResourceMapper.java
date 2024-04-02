package de.dhbw.cleanproject.adapter.category;

import de.dhbw.cleanproject.adapter.category.budget.BudgetToBudgetResourceMapper;
import de.dhbw.cleanproject.domain.category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class CategoryToCategoryResourceMapper implements Function<Category, CategoryResource> {

    private final BudgetToBudgetResourceMapper budgetToBudgetResourceMapper;

        @Override
        public CategoryResource apply(Category category) {
            return CategoryResource.builder()
                    .budget(budgetToBudgetResourceMapper.apply(category.getBudget()))
                    .id(category.getId())
                    .label(category.getName())
                    .iconName(category.getIconName())
                    .color(category.getColor())
                    .build();
        }
}
