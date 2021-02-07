package pl.sda.training.management.app.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.sda.training.management.app.domain.model.Trainer;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

@Data
@AllArgsConstructor(access = PROTECTED)
public class TrainerToShow {

    private String login;

    private String firstName;

    private String lastName;

    private List<String> editionsCodes;

    public static TrainerToShow of(Trainer trainer) {
        return new TrainerToShow(
                trainer.getUser().getLogin().value(),
                trainer.getUser().getFirstName().value(),
                trainer.getUser().getLastName().value(),
                trainer.getCoursesList().stream()
                        .map(courseEdition -> courseEdition.getEditionCode().value())
                        .collect(Collectors.toList()));
    }
}
