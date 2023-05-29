package de.dhbw.cleanproject.adapter.user;

import de.dhbw.cleanproject.adapter.category.CategoryResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class UserResource {
    private final UUID id;
    private final String  username, name;
    private final List<CategoryResource> categories;
}
