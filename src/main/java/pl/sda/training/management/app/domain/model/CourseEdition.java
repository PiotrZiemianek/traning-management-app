package pl.sda.training.management.app.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CourseEdition extends AbstractEntity {

    @ManyToOne
    private Course course;

    @Embedded
    private EditionCode editionCode;

    @ManyToMany(mappedBy = "coursesEditions")
    private Set<Student> students = new HashSet<>();

    @ManyToMany(mappedBy = "coursesList",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<Trainer> trainers = new HashSet<>();

    @OneToMany(mappedBy = "courseEdition", cascade = CascadeType.ALL)
    private List<LessonDetails> lessonsDetails = new ArrayList<>();

    public CourseEdition(Course course) {
        super();
        this.course = course;
    }

    @Builder
    private CourseEdition(Long id, Course course, EditionCode editionCode, Set<Student> students, Set<Trainer> trainers, List<LessonDetails> lessonsDetails) {
        super(id);
        this.course = course;
        this.editionCode = editionCode;
        if (students != null) this.students = students;
        if (trainers != null) this.trainers = trainers;
        if (lessonsDetails != null) this.lessonsDetails = lessonsDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseEdition that = (CourseEdition) o;

        if (!Objects.equals(id, that.id)) return false;
        return Objects.equals(editionCode, that.editionCode);
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
