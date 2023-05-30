package de.dhbw.cleanproject.adapter.user.report;

import de.dhbw.cleanproject.adapter.category.CategoryResource;
import de.dhbw.cleanproject.domain.category.Category;
import de.dhbw.cleanproject.domain.transaction.TransactionType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BiggestCategoryResource {

    private final CategoryResource category;

    private final Double amount;

}
