package pl.sda.training.management.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static pl.sda.training.management.app.domain.model.UserRole.ROLE_PARTICIPANT;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends AbstractEntity{

    @OneToOne(cascade = CascadeType.REMOVE)
    private User user;

    @ManyToMany
    private List<CourseEdition> coursesEditions = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student", orphanRemoval = true)
    private List<StudentSubmission> submissions = new ArrayList<>();

    public Student(User user) {
        this.user = user;
        user.getRoles().add(ROLE_PARTICIPANT);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (!Objects.equals(id, student.id)) return false;
        return Objects.equals(user, student.user);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
