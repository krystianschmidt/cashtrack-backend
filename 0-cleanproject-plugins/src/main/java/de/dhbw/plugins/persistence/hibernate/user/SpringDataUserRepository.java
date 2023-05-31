package de.dhbw.plugins.persistence.hibernate.user;

import de.dhbw.cleanproject.domain.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataUserRepository extends JpaRepository<AppUser, String> {

    AppUser findByUsername(String username);
    boolean existsByUsername(String username);
}
