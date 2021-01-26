package pl.sda.training.management.app.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.Login;
import pl.sda.training.management.app.domain.model.Trainer;
import pl.sda.training.management.app.domain.model.User;
import pl.sda.training.management.app.domain.repository.TrainerRepo;
import pl.sda.training.management.app.exception.TrainerNotFoundException;

import java.util.List;

import static pl.sda.training.management.app.domain.model.UserRole.ROLE_TRAINER;

@Service
@RequiredArgsConstructor
public class TrainerService {
    private final TrainerRepo trainerRepo;
    private final UserService userService;

    public Trainer save(Trainer trainer) {
        trainer.getUser()
                .setActive(true);
        return trainerRepo.save(trainer);
    }

    public void delete(Long trainerId) {
        Trainer trainer = getById(trainerId);
        trainer.getUser().setActive(false);
        trainerRepo.save(trainer);
    }

    public Trainer getByLogin(Login login) {
        return trainerRepo.findByUser_Login(login)
                .orElseThrow(() -> new TrainerNotFoundException("Trainer with login: " + login.value() + " not found."));
    }

    private Trainer getById(Long trainerId) {
        return trainerRepo.findById(trainerId).orElseThrow(() -> new TrainerNotFoundException(
                "Trainer with id: " + trainerId + " not found."));
    }

    public List<Trainer> getAll() {
        return trainerRepo.findAll();
    }

    public void setAsTrainerByLogin(Login userLogin) {
        User user = userService.getUserByLogin(userLogin);
        user.getRoles().add(ROLE_TRAINER);
        Trainer trainer = new Trainer(user);
        trainerRepo.save(trainer);
    }
}
