package de.dhbw.plugins.persistence.hibernate.user;

import de.dhbw.cleanproject.domain.user.User;
import de.dhbw.cleanproject.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepositoryBridge implements UserRepository {

    private final SpringDataUserRepository springDataUserRepository;
    @Override
    public User findByUsername(String username) {
        return springDataUserRepository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return springDataUserRepository.existsByUsername(username);
    }

    @Override
    public void save(User user) {
        springDataUserRepository.save(user);
    }


}
