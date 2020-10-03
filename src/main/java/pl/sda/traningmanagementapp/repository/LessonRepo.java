package pl.sda.traningmanagementapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.traningmanagementapp.domain.Course;
import pl.sda.traningmanagementapp.domain.Lesson;

@Repository
public interface LessonRepo extends JpaRepository<Lesson, Long> {
}
