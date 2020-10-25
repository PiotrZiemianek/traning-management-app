package pl.sda.training.management.app.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
public class CourseName implements Serializable {
    private String courseName;

    private CourseName(String courseName) {
        this.courseName = courseName;
    }

    public static CourseName of(String courseName) {
        //validation
        return new CourseName(courseName);
    }

    public String value() {
        return courseName;
    }
}
