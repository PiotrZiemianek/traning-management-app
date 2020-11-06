package pl.sda.training.management.app.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.Login;
import pl.sda.training.management.app.domain.model.Trainer;
import pl.sda.training.management.app.domain.model.User;
import pl.sda.training.management.app.domain.repository.TrainerRepo;
import pl.sda.training.management.app.exception.TrainerNotFoundException;

import java.util.List;
import java.util.Optional;

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
        Trainer trainer = getTrainerDB(trainerId);
        trainer.getUser().setActive(false);
        trainerRepo.save(trainer);
    }

    public Trainer get(Long trainerId) {
        return getTrainerDB(trainerId);
    }

    public Trainer getByLogin(Login login) {
        return trainerRepo.findByUser_Login(login)
                .orElseThrow(() -> new TrainerNotFoundException("Trainer with login: " + login.value() + " not found."));
    }

    private Trainer getTrainerDB(Long trainerId) {
        Optional<Trainer> optionalStudentSubmission = trainerRepo.findById(trainerId);
        if (optionalStudentSubmission.isPresent()) {
            return optionalStudentSubmission.get();
        }
        throw new TrainerNotFoundException("Trainer with id: " + trainerId + " not found.");
    }

    public List<Trainer> getAll() {
        return trainerRepo.findAll();
    }

    public void setAsTrainerByLogin(String userLogin) {
        User user = userService.getUserByLogin(userLogin);
        user.getRoles().add(ROLE_TRAINER);
        Trainer trainer = new Trainer(user);
        trainerRepo.save(trainer);
    }
}
