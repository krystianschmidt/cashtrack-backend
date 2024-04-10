package de.krystianschmidt.cashtrack.adapter.user.report;

import de.krystianschmidt.cashtrack.adapter.category.CategoryResource;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BiggestCategoryResource {

    private final CategoryResource category;

    private final Double amount;

}
