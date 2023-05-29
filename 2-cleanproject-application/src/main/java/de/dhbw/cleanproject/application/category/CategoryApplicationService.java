package de.dhbw.cleanproject.application.category;

import de.dhbw.cleanproject.domain.category.Category;
import de.dhbw.cleanproject.domain.category.CategoryApplication;
import de.dhbw.cleanproject.domain.category.CategoryRepository;
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
}
