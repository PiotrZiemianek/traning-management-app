package pl.sda.training.management.app.web;

import lombok.Data;
import pl.sda.training.management.app.domain.model.CourseEdition;

@Data
public class CourseEditionToChoose {
    private long id;
    private String code;

    private CourseEditionToChoose(long id, String code) {
        this.id = id;
        this.code = code;
    }

    public static CourseEditionToChoose of(CourseEdition edition) {
        return new CourseEditionToChoose(edition.getId(), edition.getEditionCode().value());
    }
}
