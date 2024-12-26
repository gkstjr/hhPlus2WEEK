package io.hhplus.tdd.domain.User;

import java.util.Optional;

public interface IUserRepository {
   Optional<User> findById(long id);

    User save(User user);
}
