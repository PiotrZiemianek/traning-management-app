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
public class CourseEdition {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Course course;

    @Embedded
    private EditionCode editionCode;

    @ManyToMany
    private List<Student> participants = new ArrayList<>();

    @ManyToMany
    private List<Coach> coaches = new ArrayList<>();

    @OneToMany
    private List<LessonDetails> lessonDetailsList = new ArrayList<>();

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
