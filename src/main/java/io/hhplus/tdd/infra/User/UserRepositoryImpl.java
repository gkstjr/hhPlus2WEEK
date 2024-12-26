package io.hhplus.tdd.infra.User;

import io.hhplus.tdd.domain.User.IUserRepository;
import io.hhplus.tdd.domain.User.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements IUserRepository {

    private final JpaUserRepo jpaUserRepo;

    public UserRepositoryImpl(JpaUserRepo jpaUserRepo) {
        this.jpaUserRepo = jpaUserRepo;
    }

    @Override
    public Optional<User> findById(long id) {
        return jpaUserRepo.findById(id);
    }

    @Override
    public User save(User user) {
        return jpaUserRepo.save(user);
    }
}
