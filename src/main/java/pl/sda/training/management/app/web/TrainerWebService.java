package pl.sda.training.management.app.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.service.TrainerService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerWebService {
    private final TrainerService trainerService;

    public List<TrainerToShow> getTrainersToShow() {
        return trainerService.getAll()
                .stream()
                .map(TrainerToShow::of)
                .collect(Collectors.toList());
    }

    public void setAsTrainerByLogin(String userLogin) {
        trainerService.setAsTrainerByLogin(userLogin);
    }
}
