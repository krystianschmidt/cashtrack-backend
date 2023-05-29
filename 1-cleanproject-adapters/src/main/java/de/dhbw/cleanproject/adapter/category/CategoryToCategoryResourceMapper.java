package de.dhbw.cleanproject.adapter.category;

import de.dhbw.cleanproject.domain.category.Category;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CategoryToCategoryResourceMapper implements Function<Category, CategoryResource> {

        @Override
        public CategoryResource apply(Category category) {
            return CategoryResource.builder()
                    .id(category.getId())
                    .label(category.getName())
                    .iconName(category.getIconName())
                    .color(category.getColor())
                    .build();
        }
}
