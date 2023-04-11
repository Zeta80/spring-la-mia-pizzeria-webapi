package org.lessons.pizzery.springlamiapizzeriacrud.repository;

import java.util.Optional;
import org.lessons.pizzery.springlamiapizzeriacrud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional<User> findByEmail(String email);
}
