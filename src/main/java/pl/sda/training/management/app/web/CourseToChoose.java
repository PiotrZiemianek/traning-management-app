package pl.sda.training.management.app.web;

import lombok.Data;
import pl.sda.training.management.app.domain.model.Course;

@Data
public class CourseToChoose {
    private long id;
    private String name;

    protected CourseToChoose(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CourseToChoose of(Course course) {
        return new CourseToChoose(course.getId(), course.getName().value());
    }
}
