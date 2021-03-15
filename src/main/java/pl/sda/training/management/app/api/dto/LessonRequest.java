package pl.sda.training.management.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import pl.sda.training.management.app.domain.model.Lesson;
import pl.sda.training.management.app.domain.model.LessonSubject;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class LessonRequest {
    private final Long id;
    private final String subject;

    public static LessonRequest of(Lesson lesson) {
        return new LessonRequest(
                lesson.getId(),
                lesson.getSubject().value());
    }

    public Lesson toLesson() {
        Lesson lesson = new Lesson(LessonSubject.of(subject));
        lesson.setId(id);
        return lesson;
    }
}
