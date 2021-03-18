package pl.sda.training.management.app.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Trainer extends AbstractEntity {

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "trainer")
    private List<LessonDetails> lessonDetails = new ArrayList<>();

    @ManyToMany
    private Set<CourseEdition> coursesList = new HashSet<>();

    public Trainer(User user) {
        this.user = user;
    }

    @Builder
    private Trainer(Long id, User user, List<LessonDetails> lessonDetails, Set<CourseEdition> coursesList) {
        super(id);
        this.user = user;
        if (lessonDetails != null) this.lessonDetails = lessonDetails;
        if (coursesList != null) this.coursesList = coursesList;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trainer trainer = (Trainer) o;

        if (!Objects.equals(id, trainer.id)) return false;
        return Objects.equals(user, trainer.user);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
