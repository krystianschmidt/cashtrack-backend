package de.krystianschmidt.cashtrack.adapter.transaction;

import de.krystianschmidt.cashtrack.adapter.category.CategoryDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDto {
    private Double amount;
    private CategoryDto category;
    private String description;
    private LocalDateTime timestamp;
}
