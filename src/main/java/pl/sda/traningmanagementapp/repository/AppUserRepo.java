package pl.sda.traningmanagementapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.traningmanagementapp.domain.AppUser;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, Long> {
    AppUser findByLogin(String login);
}
