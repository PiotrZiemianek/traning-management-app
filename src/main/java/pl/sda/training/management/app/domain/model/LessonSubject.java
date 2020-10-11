package pl.sda.training.management.app.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
public class LessonSubject {

    private String lessonSubject;

    private LessonSubject(String lessonSubject) {
        this.lessonSubject = lessonSubject;
    }

    static LessonSubject of(String lessonSubject) {
        //validation
        return new LessonSubject(lessonSubject);
    }

    String value() {
        return lessonSubject;
    }
}
