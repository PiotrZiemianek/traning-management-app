package pl.sda.training.management.app.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.Notification;
import pl.sda.training.management.app.domain.model.Student;
import pl.sda.training.management.app.domain.model.User;
import pl.sda.training.management.app.domain.model.UserNotification;
import pl.sda.training.management.app.domain.repository.NotificationRepo;
import pl.sda.training.management.app.domain.repository.StudentRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepo notificationRepo;
    private final StudentRepo studentRepo;

    public void setNotificationAsReadByUser(Notification notification, User user) {
        UserNotification userNotification = user.getNotifications();
        if (userNotification.getUnreadNotifications().contains(notification)) {

            userNotification.getUnreadNotifications().remove(notification);
            userNotification.getReadNotifications().add(notification);
            notification.getReadByUsers().add(user);
            notificationRepo.save(notification);
        }

    }

    public void notifyStudents(Notification notification, List<Student> students) {
        students.stream()
                .map(Student::getUser)
                .forEach(user -> {
                    user.getNotifications().getUnreadNotifications().add(notification);
                });

        studentRepo.saveAll(students);

    }
}
