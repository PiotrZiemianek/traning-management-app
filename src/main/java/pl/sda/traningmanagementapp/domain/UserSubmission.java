package pl.sda.traningmanagementapp.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Data
public class UserSubmission {
    @Id
    private Long id;

    private LocalDate submissionDate;

    @ManyToOne
    private AppUser participant;

    @ManyToOne
    private Course course;
}
