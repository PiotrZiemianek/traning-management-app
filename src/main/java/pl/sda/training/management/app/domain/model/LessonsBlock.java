package pl.sda.training.management.app.domain.model;

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
public class LessonsBlock {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private BlockName blockName;

    @ManyToOne
    private Course course;

    @OneToMany(mappedBy = "lessonsBlock", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons = new ArrayList<>();

    public LessonsBlock(BlockName blockName) {
        this.blockName = blockName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LessonsBlock that = (LessonsBlock) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return blockName != null ? blockName.equals(that.blockName) : that.blockName == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (blockName != null ? blockName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LessonsBlock{" +
                "id=" + id +
                ", blockName='" + blockName + '\'' +
                ", lessons=" + lessons +
                '}';
    }
}
