package de.krystianschmidt.cashtrack.plugins.persistence.hibernate.category;

import de.krystianschmidt.cashtrack.domain.category.Category;
import de.krystianschmidt.cashtrack.domain.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryBridge implements CategoryRepository {

    private final SpringDataCategoryRepository repository;


    @Override
    public void saveCategory(Category category) {
        repository.save(category);
    }

    @Override
    public void removeCategory(UUID categoryId) {
        repository.deleteById(categoryId);
    }

    @Override
    public void updateCategory(Category category) {

    }

    @Override
    public Category getCategory(UUID categoryId) {
        return repository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }


}
