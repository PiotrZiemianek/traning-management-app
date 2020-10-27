package pl.sda.training.management.app.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sda.training.management.app.domain.model.Lesson;
import pl.sda.training.management.app.domain.model.LessonSubject;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class LessonDTO {
    private Long id;
    private String subject;

    public static LessonDTO of(Lesson lesson) {
        return new LessonDTO(
                lesson.getId(),
                lesson.getSubject().value());
    }

    public Lesson toLesson() {
        Lesson lesson = new Lesson(LessonSubject.of(subject));
        lesson.setId(id);
        return lesson;
    }
}
