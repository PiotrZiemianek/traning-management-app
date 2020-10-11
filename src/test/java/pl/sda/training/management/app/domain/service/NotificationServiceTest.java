package pl.sda.training.management.app.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sda.training.management.app.domain.model.Notification;
import pl.sda.training.management.app.domain.model.Student;
import pl.sda.training.management.app.domain.model.User;
import pl.sda.training.management.app.domain.model.UserNotification;
import pl.sda.training.management.app.domain.repository.NotificationRepo;
import pl.sda.training.management.app.domain.repository.StudentRepo;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {
    private NotificationService sut;

    @Mock
    private NotificationRepo notificationRepo;

    @Mock
    private StudentRepo studentRepo;

    @BeforeEach
    void setup() {
        sut = new NotificationService(notificationRepo, studentRepo);
    }

    @Test
    @DisplayName("Should flip notification from user's unread notifications to read notifications. " +
            "Should add user to notification's readByUsers list and save notification in DB.")
    void setNotificationAsReadByUser() {
        //given
        Notification notification = new Notification();

        User user = new User();
        user.setNotifications(new UserNotification());
        user.getNotifications().getUnreadNotifications().add(notification);

        //when
        sut.setNotificationAsReadByUser(notification, user);

        //then
        assertThat(user.getNotifications().getUnreadNotifications()).isEmpty();
        assertThat(user.getNotifications().getReadNotifications()).isNotEmpty();
        assertThat(notification.getReadByUsers()).isNotEmpty();
        verify(notificationRepo, atLeastOnce()).save(notification);

    }

    @Test
    void notifyStudents() {
        //given
        Notification notification = new Notification();

        List<Student> students = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            UserNotification userNotification = new UserNotification();
            User user = new User();
            Student student = new Student();
            user.setNotifications(userNotification);
            student.setUser(user);
            students.add(student);
        }
        //when
        sut.notifyStudents(notification, students);
        //then
        students.stream()
                .map(student -> student.getUser().getNotifications())
                .forEach(userNotification ->
                        assertThat(userNotification.getUnreadNotifications()).isNotEmpty());
        verify(studentRepo, atLeastOnce()).saveAll(students);
    }
}