package pl.sda.training.management.app.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@Builder
public class CourseEdition {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Course course;

    @Embedded
    private EditionCode editionCode;

    @ManyToMany(mappedBy = "coursesEditions")
    private Set<Student> students = new HashSet<>();

    @ManyToMany(mappedBy = "coursesList",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<Trainer> trainers = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<LessonDetails> lessonsDetails = new ArrayList<>();

    public CourseEdition(Course course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseEdition that = (CourseEdition) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return editionCode != null ? editionCode.equals(that.editionCode) : that.editionCode == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (editionCode != null ? editionCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CourseEdition{" +
                "id=" + id +
                ", course=" + course +
                ", editionCode=" + editionCode +
                '}';
    }

}
