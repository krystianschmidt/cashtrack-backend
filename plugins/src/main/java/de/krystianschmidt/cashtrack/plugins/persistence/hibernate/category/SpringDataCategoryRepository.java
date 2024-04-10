package de.krystianschmidt.cashtrack.plugins.persistence.hibernate.category;

import de.krystianschmidt.cashtrack.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataCategoryRepository extends JpaRepository<Category, UUID> {


}

