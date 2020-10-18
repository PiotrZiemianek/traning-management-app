package pl.sda.training.management.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trainer {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "trainer")
    private List<LessonDetails> lessonDetails = new ArrayList<>();

    @ManyToMany
    private List<LessonsBlock> lessonsBlocks = new ArrayList<>();

    @ManyToMany
    private List<CourseEdition> coursesList = new ArrayList<>();


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

        if (id != null ? !id.equals(trainer.id) : trainer.id != null) return false;
        return user != null ? user.equals(trainer.user) : trainer.user == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
