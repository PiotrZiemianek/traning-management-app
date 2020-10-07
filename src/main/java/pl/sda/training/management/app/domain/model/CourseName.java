package pl.sda.training.management.app.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
public class CourseName {
    private String courseName;

    private CourseName(String courseName) {
        this.courseName = courseName;
    }
    static CourseName of(String courseName){
        //validation
        return new CourseName(courseName);
    }
}
