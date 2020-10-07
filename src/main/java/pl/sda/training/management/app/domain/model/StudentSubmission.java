package pl.sda.training.management.app.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StudentSubmission {
    @Id
    @GeneratedValue
    private Long id;

    @CreationTimestamp
    private LocalDate submissionDate;

    @ManyToOne
    private Student student;

    @ManyToOne
    private CourseEdition courseEdition;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentSubmission that = (StudentSubmission) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return submissionDate != null ? submissionDate.equals(that.submissionDate) : that.submissionDate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (submissionDate != null ? submissionDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StudentSubmission{" +
                "id=" + id +
                ", submissionDate=" + submissionDate +
                ", student=" + student +
                ", courseEdition=" + courseEdition +
                '}';
    }
}
