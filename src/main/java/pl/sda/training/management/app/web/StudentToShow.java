package pl.sda.training.management.app.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.sda.training.management.app.domain.model.Student;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor(access = PRIVATE)
public class StudentToShow {

    private String login;

    private String firstName;

    private String lastName;

    private List<String> editionsCodes;

    public static StudentToShow of(Student student) {
        return new StudentToShow(
                student.getUser().getLogin().value(),
                student.getUser().getFirstName().value(),
                student.getUser().getLastName().value(),
                student.getCoursesEditions().stream()
                        .map(courseEdition -> courseEdition.getEditionCode().value())
                        .collect(Collectors.toList()));
    }
}
