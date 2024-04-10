package de.krystianschmidt.cashtrack.adapter.transaction;

import de.krystianschmidt.cashtrack.adapter.category.CategoryResource;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class TransactionResource {
    private final UUID id;
    private final String description;
    private final LocalDateTime timestamp;
    private final Double amount;
    private final CategoryResource category;
}
