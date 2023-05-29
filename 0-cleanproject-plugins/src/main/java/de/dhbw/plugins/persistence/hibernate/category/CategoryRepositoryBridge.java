package de.dhbw.plugins.persistence.hibernate.category;

import de.dhbw.cleanproject.domain.category.Category;
import de.dhbw.cleanproject.domain.category.CategoryRepository;
import de.dhbw.cleanproject.domain.user.User;
import de.dhbw.plugins.persistence.hibernate.transaction.SpringDataTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryBridge implements CategoryRepository {

    private final SpringDataCategoryRepository repository;


    @Override
    public void addCategory(Category category) {
        repository.save(category);
    }

    @Override
    public void removeCategory(UUID categoryId) {
        repository.deleteById(categoryId);
    }

    @Override
    public void updateCategory(Category category) {

    }

}
