package de.dhbw.plugins.persistence.hibernate.category;

import de.dhbw.cleanproject.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataCategoryRepository extends JpaRepository<Category, UUID> {


}

