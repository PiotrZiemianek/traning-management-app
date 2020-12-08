package pl.sda.training.management.app.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.training.management.app.domain.model.EditionCode;
import pl.sda.training.management.app.domain.model.Login;
import pl.sda.training.management.app.domain.model.StudentSubmission;

@Repository
public interface StudentSubmissionRepo extends JpaRepository<StudentSubmission, Long> {
    boolean existsByCourseEdition_EditionCodeAndStudent_User_Login(EditionCode editionCode, Login studentLogin);

}
