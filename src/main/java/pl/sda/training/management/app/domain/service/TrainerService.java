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
        Optional<Trainer> optionalTrainer = trainerRepo.findById(trainerId);

        if (optionalTrainer.isPresent()) {
            optionalTrainer.get()
                    .getUser().setActive(false);
            return;
        }
        throw new TrainerNotFoundException("Trainer with id: " + trainerId + " not found.");

    }
}
