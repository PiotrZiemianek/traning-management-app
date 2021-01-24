package pl.sda.training.management.app.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.sda.training.management.app.domain.model.CourseEdition;
import pl.sda.training.management.app.domain.model.EditionCode;
import pl.sda.training.management.app.domain.model.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseEditionRepo extends JpaRepository<CourseEdition, Long> {

    @Query("select editionCode from CourseEdition where course.id = :courseId and :student not member students")
    List<EditionCode> getEditionCodesByCourseIdWhereStudentNotParticipated(@Param("courseId") Long courseId, @Param("student") Student student);

    Optional<CourseEdition> findByEditionCode(EditionCode code);

    @Query("select editionCode from CourseEdition where course.id = :courseId")
    List<EditionCode> getEditionCodesByCourseId(@Param("courseId") Long courseId);

    @Query("select editionCode from CourseEdition where :student not member students")
    List<EditionCode> getEditionCodesWhereStudentIsNotParticipant(@Param("student") Student student);

    boolean existsByEditionCode(EditionCode editionCode);
}
