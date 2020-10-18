package pl.sda.training.management.app.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserNotification {
    @Id
    private Long id;

    @OneToOne
    private User user;

    @ManyToMany
    private List<Notification> readNotifications = new ArrayList<>();

    @ManyToMany
    private List<Notification> unreadNotifications = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserNotification that = (UserNotification) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UserNotification{" +
                "id=" + id +
                ", readNotifications=" + readNotifications +
                ", unreadNotifications=" + unreadNotifications +
                '}';
    }
}
