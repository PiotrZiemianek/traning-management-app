package pl.sda.training.management.app.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
public class NotificationContent implements Serializable {

    private String notificationContent;

    private NotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    static NotificationContent of(String notificationContent) {
        //validation
        return new NotificationContent(notificationContent);
    }

    String value() {
        return notificationContent;
    }
}
