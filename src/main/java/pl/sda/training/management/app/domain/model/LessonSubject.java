package pl.sda.training.management.app.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
public class LessonSubject implements Serializable {

    private String lessonSubject;

    private LessonSubject(String lessonSubject) {
        this.lessonSubject = lessonSubject;
    }

    public static LessonSubject of(String lessonSubject) {
        //validation
        return new LessonSubject(lessonSubject);
    }

   public String value() {
        return lessonSubject;
    }
}
