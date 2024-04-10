package de.krystianschmidt.cashtrack.adapter.user;

import de.krystianschmidt.cashtrack.adapter.category.CategoryResource;
import de.krystianschmidt.cashtrack.adapter.user.report.ReportResource;
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
    private final List<ReportResource> reports;
}
