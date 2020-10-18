package pl.sda.training.management.app.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.training.management.app.domain.model.CourseEdition;

@Repository
public interface CourseEditionRepo extends JpaRepository<CourseEdition, Long> {
}
