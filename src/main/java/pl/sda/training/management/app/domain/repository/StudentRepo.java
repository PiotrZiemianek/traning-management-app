package pl.sda.training.management.app.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.training.management.app.domain.model.Student;

import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
    public Optional<Student> findByUser_Id(Long user_id);
}