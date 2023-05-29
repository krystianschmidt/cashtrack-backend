package de.dhbw.plugins.persistence.hibernate.category;

import de.dhbw.cleanproject.domain.category.Category;
import de.dhbw.cleanproject.domain.transaction.Transaction;
import de.dhbw.cleanproject.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataCategoryRepository extends JpaRepository<Category, UUID> {


}

