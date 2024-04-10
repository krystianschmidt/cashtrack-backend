package de.krystianschmidt.cashtrack.adapter.category;

import de.krystianschmidt.cashtrack.domain.category.Category;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CategoryDtoToCategoryMapper implements Function<CategoryDto, Category> {
    @Override
    public Category apply(CategoryDto categoryDto) {
        return Category.builder()
                .color(categoryDto.getColor())
                .iconName(categoryDto.getIconName())
                .name(categoryDto.getLabel())
                .id(categoryDto.getId())
                .build();
    }
}
