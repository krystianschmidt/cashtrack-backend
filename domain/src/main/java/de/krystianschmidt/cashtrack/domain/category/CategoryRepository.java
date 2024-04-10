package de.krystianschmidt.cashtrack.domain.category;

import java.util.UUID;

public interface CategoryRepository {

    void saveCategory(Category category);

    void removeCategory(UUID categoryId);

    void updateCategory(Category category);

    Category getCategory(UUID categoryId);

}
