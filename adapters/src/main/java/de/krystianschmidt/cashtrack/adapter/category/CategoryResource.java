package de.krystianschmidt.cashtrack.adapter.category;

import de.krystianschmidt.cashtrack.adapter.category.budget.BudgetResource;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class CategoryResource {
    private final UUID id;
    private final String color;
    private final String iconName;
    private final String label;

    private final BudgetResource budget;
}
