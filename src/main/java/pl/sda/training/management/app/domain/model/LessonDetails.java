package pl.sda.training.management.app.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LessonDetails extends AbstractEntity {

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

    @Builder
    private LessonDetails(Long id, Lesson lesson, CourseEdition courseEdition, LocalDateTime localDateTime, Duration duration, Trainer trainer, Address address, List<Notification> notifications) {
        super(id);
        this.lesson = lesson;
        this.courseEdition = courseEdition;
        this.localDateTime = localDateTime;
        this.duration = duration;
        this.trainer = trainer;
        this.address = address;
        if (notifications != null) this.notifications = notifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LessonDetails that = (LessonDetails) o;

        if (!Objects.equals(id, that.id)) return false;
        return Objects.equals(lesson, that.lesson);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (lesson != null ? lesson.hashCode() : 0);
        return result;
    }
}
