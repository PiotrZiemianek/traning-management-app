package pl.sda.training.management.app.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserSubmission {
    @Id
    private Long id;

    private LocalDate submissionDate;

    @ManyToOne
    private AppUser participant;

    @ManyToOne
    private Course course;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSubmission that = (UserSubmission) o;

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
        return "UserSubmission{" +
                "id=" + id +
                ", submissionDate=" + submissionDate +
                ", participant=" + participant +
                ", course=" + course +
                '}';
    }
}
