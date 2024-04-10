package de.krystianschmidt.cashtrack.adapter.user.report;

import de.krystianschmidt.cashtrack.adapter.category.CategoryToCategoryResourceMapper;
import de.krystianschmidt.cashtrack.domain.user.report.BiggestCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BiggestCategoryToBiggestResourceMapper implements Function<BiggestCategory, BiggestCategoryResource> {

    private final CategoryToCategoryResourceMapper categoryToCategoryResourceMapper;

    @Override
    public BiggestCategoryResource apply(BiggestCategory biggestCategory) {
        if (biggestCategory == null || biggestCategory.getCategory() == null) {
            return null;
        }
        return BiggestCategoryResource.builder()
                .category(categoryToCategoryResourceMapper.apply(biggestCategory.getCategory()))
                .amount(biggestCategory.getAmount())
                .build();
    }
}
