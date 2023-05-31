package de.dhbw.cleanproject.domain.user;

public interface UserRepository {

    AppUser findByUsername(String username);
    boolean existsByUsername(String username);

    void save(AppUser user);

}
