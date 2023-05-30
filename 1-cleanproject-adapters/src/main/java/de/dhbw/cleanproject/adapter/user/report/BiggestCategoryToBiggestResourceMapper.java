package de.dhbw.cleanproject.adapter.user.report;

import de.dhbw.cleanproject.adapter.category.CategoryToCategoryResourceMapper;
import de.dhbw.cleanproject.domain.category.Category;
import de.dhbw.cleanproject.domain.user.report.BiggestCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BiggestCategoryToBiggestResourceMapper implements Function<BiggestCategory, BiggestCategoryResource> {

    private final CategoryToCategoryResourceMapper categoryToCategoryResourceMapper;

    @Override
    public BiggestCategoryResource apply(BiggestCategory biggestCategory) {
        return BiggestCategoryResource.builder()
                .category(categoryToCategoryResourceMapper.apply(biggestCategory.getCategory()))
                .amount(biggestCategory.getAmount())
                .build();
    }
}
