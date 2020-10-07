package pl.sda.training.management.app.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.training.management.app.domain.model.Login;
import pl.sda.training.management.app.domain.model.User;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByLogin(Login login);
}
