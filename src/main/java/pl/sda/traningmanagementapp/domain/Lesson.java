package pl.sda.traningmanagementapp.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
@Entity
@Data
public class Lesson {
    @Id
    private Long id;
    private String subject;
    private LocalDate date;

}
