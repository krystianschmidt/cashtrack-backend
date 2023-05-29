package de.dhbw.cleanproject.adapter.transaction;

import de.dhbw.cleanproject.adapter.category.CategoryDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionDto {
    private Double amount;
    private CategoryDto category;
    private String description;
    private LocalDateTime timestamp;
}
