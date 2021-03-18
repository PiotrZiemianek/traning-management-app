package pl.sda.training.management.app.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Notification extends AbstractEntity{

    @ManyToOne
    private LessonDetails lessonDetails;

    @Embedded
    private NotificationSubject subject;

    @Embedded
    private NotificationContent content;

    @ManyToMany
    private List<User> readByUsers = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notification that = (Notification) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(subject, that.subject)) return false;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", subject=" + subject +
                ", content=" + content +
                '}';
    }
}
