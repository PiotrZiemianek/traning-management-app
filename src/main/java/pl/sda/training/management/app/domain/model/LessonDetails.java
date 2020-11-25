package pl.sda.training.management.app.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@Builder
public class LessonDetails {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Lesson lesson;

    @ManyToOne
    private CourseEdition courseEdition;

    private LocalDateTime localDateTime;

    private Duration duration;

    @ManyToOne
    private Trainer trainer;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "lessonDetails")
    private List<Notification> notifications = new ArrayList<>();

    public LessonDetails(Lesson lesson) {
        this.lesson = lesson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LessonDetails that = (LessonDetails) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return lesson != null ? lesson.equals(that.lesson) : that.lesson == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (lesson != null ? lesson.hashCode() : 0);
        return result;
    }
}
