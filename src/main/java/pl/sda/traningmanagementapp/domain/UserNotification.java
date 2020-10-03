package pl.sda.traningmanagementapp.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class UserNotification {
    @Id
    private Long id;

    @OneToMany
    private List<Notification> readNotifications = new ArrayList<>();

    @OneToMany
    private List<Notification> unreadNotifications = new ArrayList<>();

}
