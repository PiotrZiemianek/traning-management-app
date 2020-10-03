package pl.sda.traningmanagementapp.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
public class Notification {
    @Id
    private Long id;

    @ManyToOne
    private Lesson lesson;

    private String subject;

    private String content;
}
