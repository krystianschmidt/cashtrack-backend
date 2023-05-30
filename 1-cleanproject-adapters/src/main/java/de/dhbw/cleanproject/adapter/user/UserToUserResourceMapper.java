package de.dhbw.cleanproject.adapter.user;

import de.dhbw.cleanproject.adapter.category.CategoryResource;
import de.dhbw.cleanproject.adapter.category.CategoryToCategoryResourceMapper;
import de.dhbw.cleanproject.adapter.user.UserResource;
import de.dhbw.cleanproject.adapter.user.report.ReportResource;
import de.dhbw.cleanproject.adapter.user.report.ReportToReportResourceMapper;
import de.dhbw.cleanproject.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserToUserResourceMapper implements Function<User, UserResource> {

    private final CategoryToCategoryResourceMapper categoryToCategoryResourceMapper;
    private final ReportToReportResourceMapper reportToReportResourceMapper;

    @Override
    public UserResource apply(final User user) {
        return map(user);
    }

    private UserResource map(final User user) {
        List<CategoryResource> categoryResourceList = user.getCategories().stream()
                .map(categoryToCategoryResourceMapper)
                .collect(Collectors.toList());

        List<ReportResource> reportResources = user.getReports().stream()
                .map(reportToReportResourceMapper)
                .collect(Collectors.toList());

        return UserResource.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .categories(categoryResourceList)
                .reports(reportResources)
                .build();
    }

}
