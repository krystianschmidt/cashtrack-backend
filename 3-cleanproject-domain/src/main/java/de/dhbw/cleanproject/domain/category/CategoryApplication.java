package de.dhbw.cleanproject.domain.category;

import java.util.List;
import java.util.UUID;

public interface CategoryApplication {

    void addCategory(Category category);

    void removeCategory(UUID categoryId);

    void updateCategory(Category category);

    List<Category> getCategories();


    void addBudget(UUID categoryId, Budget budget);

    void removeBudget(UUID categoryID);

    void refreshBudget(Category category);


    Category getCategory(UUID id);
}
