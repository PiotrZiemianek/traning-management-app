package pl.sda.training.management.app.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LessonBlock {
    @Id
    private Long id;

    private String nameOfBlock;

    @OneToMany
    private List<Lesson> lessons = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LessonBlock that = (LessonBlock) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return nameOfBlock != null ? nameOfBlock.equals(that.nameOfBlock) : that.nameOfBlock == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nameOfBlock != null ? nameOfBlock.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LessonBlock{" +
                "id=" + id +
                ", nameOfBlock='" + nameOfBlock + '\'' +
                ", lessons=" + lessons +
                '}';
    }
}
