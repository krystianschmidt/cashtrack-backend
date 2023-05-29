package de.dhbw.cleanproject.application.category;

import de.dhbw.cleanproject.domain.category.Budget;
import de.dhbw.cleanproject.domain.category.Category;
import de.dhbw.cleanproject.domain.category.CategoryApplication;
import de.dhbw.cleanproject.domain.category.CategoryRepository;
import de.dhbw.cleanproject.domain.transaction.Transaction;
import de.dhbw.cleanproject.domain.user.User;
import de.dhbw.cleanproject.domain.user.UserApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryApplicationService implements CategoryApplication {

    private final CategoryRepository categoryRepository;
    private final UserApplication userApplication;

    @Override
    public void addCategory(Category category) {
        userApplication.addCategory(category);
    }

    @Override
    public void removeCategory(UUID categoryId) {
        User user = userApplication.getUser();
        Optional<Category> category = user.getCategories().stream()
                .filter(c -> c.getId().equals(categoryId))
                .findFirst();

        if(category.isEmpty())
            throw new IllegalArgumentException("Category not found");

        user.getCategories().remove(category.get());
        userApplication.save(user);
    }

    @Override
    public void updateCategory(Category category) {

    }

    @Override
    public List<Category> getCategories() {
        return userApplication.getUser().getCategories();
    }

    @Override
    public void addBudget(UUID categoryId, Budget budget) {
        User user = userApplication.getUser();
        Optional<Category> optionalCategory = user.getCategories().stream().filter(c -> c.getId().equals(categoryId)).findFirst();
        if(optionalCategory.isEmpty())
            throw new RuntimeException("Category does not belong to user");

        Category category = optionalCategory.get();
        category.setBudget(budget);
        categoryRepository.saveCategory(category);
    }

    @Override
    public void removeBudget(UUID categoryId) {
        User user = userApplication.getUser();
        Optional<Category> optionalCategory = user.getCategories().stream().filter(c -> c.getId().equals(categoryId)).findFirst();
        if(optionalCategory.isEmpty())
            throw new RuntimeException("Category does not belong to user");

        Category category = optionalCategory.get();
        category.setBudget(null);
        categoryRepository.saveCategory(category);
    }


}
