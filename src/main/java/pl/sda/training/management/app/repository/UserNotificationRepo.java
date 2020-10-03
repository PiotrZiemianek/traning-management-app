package pl.sda.training.management.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.training.management.app.domain.UserNotification;

@Repository
public interface UserNotificationRepo extends JpaRepository<UserNotification, Long> {
}
