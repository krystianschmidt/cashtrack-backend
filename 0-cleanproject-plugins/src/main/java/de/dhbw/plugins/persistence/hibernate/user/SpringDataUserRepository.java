package de.dhbw.plugins.persistence.hibernate.user;

import de.dhbw.cleanproject.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataUserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);
    boolean existsByUsername(String username);
}
