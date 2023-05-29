package de.dhbw.cleanproject.adapter.category;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateCategoryDto {
    private String color;
    private String iconName;
    private String label;
}
