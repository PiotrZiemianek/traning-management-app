package pl.sda.training.management.app.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.sda.training.management.app.domain.model.CourseEdition;

import static lombok.AccessLevel.PROTECTED;

@Data
@AllArgsConstructor(access = PROTECTED)
public class CourseEditionToChoose {
    private long id;
    private String code;
    private String courseName;

    public static CourseEditionToChoose of(CourseEdition edition) {
        return new CourseEditionToChoose(
                edition.getId(),
                edition.getEditionCode().value(),
                edition.getCourse().getName().value());
    }
}
