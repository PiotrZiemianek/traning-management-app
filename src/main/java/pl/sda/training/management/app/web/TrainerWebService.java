package pl.sda.training.management.app.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.Trainer;
import pl.sda.training.management.app.domain.model.User;
import pl.sda.training.management.app.domain.service.TrainerService;
import pl.sda.training.management.app.domain.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

import static pl.sda.training.management.app.domain.model.UserRole.ROLE_TRAINER;

@Service
@RequiredArgsConstructor
public class TrainerWebService {
    private final TrainerService trainerService;
    private final UserService userService;


    public List<TrainerToShow> getTrainersToShow() {
        return trainerService.getAll()
                .stream()
                .map(TrainerToShow::of)
                .collect(Collectors.toList());
    }

    public void setAsTrainerByLogin(String userLogin) {
        User user = userService.getUserByLogin(userLogin);
        user.getRoles().add(ROLE_TRAINER);
        Trainer trainer = new Trainer(user);
        trainerService.save(trainer);
    }
}
