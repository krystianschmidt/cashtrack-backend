package de.krystianschmidt.cashtrack.domain.user;

public interface UserRepository {

    User findByUsername(String username);
    boolean existsByUsername(String username);

    void save(User user);

}
