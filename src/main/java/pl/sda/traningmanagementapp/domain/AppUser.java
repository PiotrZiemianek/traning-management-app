package pl.sda.traningmanagementapp.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class AppUser {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String login;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String firstName;

    private String lastName;

    private boolean isActive;

    @ManyToMany(mappedBy = "participants")
    private List<Course> courses = new ArrayList<>();

    @OneToOne
    private UserNotification notifications;


}
