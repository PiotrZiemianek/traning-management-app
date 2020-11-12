package pl.sda.training.management.app.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.training.management.app.domain.model.Login;
import pl.sda.training.management.app.domain.model.Trainer;

import java.util.Optional;

@Repository
public interface TrainerRepo extends JpaRepository<Trainer, Long> {
    Optional<Trainer> findByUser_Login(Login login);
}
