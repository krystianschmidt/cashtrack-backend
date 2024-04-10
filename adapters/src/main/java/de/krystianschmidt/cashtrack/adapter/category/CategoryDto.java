package de.krystianschmidt.cashtrack.adapter.category;

import lombok.Data;

import java.util.UUID;

@Data
public class CategoryDto {
    private String color;
    private String iconName;
    private String label;
    private UUID id;
}
