package io.hhplus.tdd.infra.User;

import io.hhplus.tdd.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepo extends JpaRepository<User , Long> {

}
