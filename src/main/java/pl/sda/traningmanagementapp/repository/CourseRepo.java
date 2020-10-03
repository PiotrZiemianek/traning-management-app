package pl.sda.traningmanagementapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.traningmanagementapp.domain.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
}
