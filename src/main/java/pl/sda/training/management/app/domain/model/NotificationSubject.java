package pl.sda.training.management.app.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
public class NotificationSubject {

    private String notificationSubject;

    private NotificationSubject(String notificationSubject) {
        this.notificationSubject = notificationSubject;
    }

    static NotificationSubject of(String notificationSubject) {
        //validation
        return new NotificationSubject(notificationSubject);
    }

    String value() {
        return notificationSubject;
    }
}
