package pl.sda.traningmanagementapp.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class LessonBlock {
    @Id
    private Long id;

    private String nameOfBlock;

    @OneToMany
    private List<Lesson> lessons = new ArrayList<>();

}
