package pl.sda.training.management.app.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserNotification extends AbstractEntity{

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

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UserNotification{" +
                "id=" + id +
                ", readNotifications=" + readNotifications.size() +
                ", unreadNotifications=" + unreadNotifications.size() +
                '}';
    }
}
