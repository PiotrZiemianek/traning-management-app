package pl.sda.traningmanagementapp.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Course {
    @Id
    private Long id;

    private String name;

    @OneToMany
    private List<LessonBlock> lessonBlocks = new ArrayList<>();

    @ManyToMany
    private List<AppUser> participants = new ArrayList<>();
}
