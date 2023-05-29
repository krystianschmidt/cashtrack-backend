package de.dhbw.cleanproject.adapter.category;

import de.dhbw.cleanproject.domain.category.Category;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CreateCategoryDtoToCategoryMapper implements Function<CreateCategoryDto, Category> {
    @Override
    public Category apply(CreateCategoryDto categoryDto) {
        return Category.builder()
                .color(categoryDto.getColor())
                .iconName(categoryDto.getIconName())
                .name(categoryDto.getLabel())
                .build();
    }
}
