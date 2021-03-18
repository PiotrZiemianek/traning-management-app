package pl.sda.training.management.app.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Lesson extends AbstractEntity{

    @Embedded
    private LessonSubject subject;

    @ManyToOne
    private LessonsBlock lessonsBlock;

    public Lesson(LessonSubject subject) {
        this.subject = subject;
    }

    public Lesson(LessonSubject subject, LessonsBlock lessonsBlock) {
        this.subject = subject;
        this.lessonsBlock = lessonsBlock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lesson lesson = (Lesson) o;

        if (!Objects.equals(id, lesson.id)) return false;
        return Objects.equals(subject, lesson.subject);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", subject=" + subject +
                '}';
    }
}
