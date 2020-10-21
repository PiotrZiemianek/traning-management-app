package pl.sda.training.management.app.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Lesson implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

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

        if (id != null ? !id.equals(lesson.id) : lesson.id != null) return false;
        return subject != null ? subject.equals(lesson.subject) : lesson.subject == null;
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
