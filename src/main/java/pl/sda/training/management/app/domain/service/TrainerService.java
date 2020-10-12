package pl.sda.training.management.app.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.Trainer;
import pl.sda.training.management.app.domain.repository.TrainerRepo;
import pl.sda.training.management.app.exception.TrainerNotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainerService {
    private final TrainerRepo trainerRepo;

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

    private Trainer getTrainerDB(Long trainerId) {
        Optional<Trainer> optionalStudentSubmission = trainerRepo.findById(trainerId);
        if (optionalStudentSubmission.isPresent()) {
            return optionalStudentSubmission.get();
        }
        throw new TrainerNotFoundException("Trainer with id: " + trainerId + " not found.");
    }
}
