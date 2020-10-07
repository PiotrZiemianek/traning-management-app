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
public class Coach {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private User user;

    @OneToMany
    private List<LessonDetails> lessonDetails = new ArrayList<>();

    @ManyToMany
    private List<LessonsBlock> lessonsBlocks = new ArrayList<>();

    @ManyToMany
    private List<CourseEdition> coursesList = new ArrayList<>();


    @Override
    public String toString() {
        return "Coach{" +
                "id=" + id +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coach coach = (Coach) o;

        if (id != null ? !id.equals(coach.id) : coach.id != null) return false;
        return user != null ? user.equals(coach.user) : coach.user == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
