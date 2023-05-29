package de.dhbw.cleanproject.domain.category;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository {

    void addCategory(Category category);

    void removeCategory(UUID categoryId);

    void updateCategory(Category category);

}
