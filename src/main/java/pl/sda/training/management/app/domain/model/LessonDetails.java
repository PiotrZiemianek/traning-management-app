package pl.sda.training.management.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonDetails {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Lesson lesson;

    private LocalDateTime localDateTime;

    @ManyToOne
    private Trainer trainer;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "lessonDetails")
    private List<Notification> notifications = new ArrayList<>();
}
