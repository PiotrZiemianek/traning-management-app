package pl.sda.training.management.app.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sda.training.management.app.domain.model.CourseEdition;
import pl.sda.training.management.app.domain.model.Student;
import pl.sda.training.management.app.domain.model.StudentSubmission;
import pl.sda.training.management.app.domain.repository.CourseEditionRepo;
import pl.sda.training.management.app.domain.repository.StudentRepo;
import pl.sda.training.management.app.domain.repository.StudentSubmissionRepo;
import pl.sda.training.management.app.exception.StudentSubmissionNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class StudentSubmissionServiceTest {
    private StudentSubmissionService sut;
    @Mock
    private StudentSubmissionRepo studentSubmissionRepo;
    @Mock
    private CourseEditionRepo courseEditionRepo;
    @Mock
    private StudentRepo studentRepo;

    @BeforeEach
    void setup() {
        sut = new StudentSubmissionService(studentSubmissionRepo, courseEditionRepo, studentRepo);
    }

    @Test
    @DisplayName("Should add student to courseEdition and opposite. " +
            "Should delete studentSubmission from DB.")
    void acceptSubmission() {
        //given
        long submissionId = 1L;

        Student student = new Student();

        CourseEdition courseEdition = new CourseEdition();

        StudentSubmission studentSubmission = new StudentSubmission();
        studentSubmission.setStudent(student);
        studentSubmission.setCourseEdition(courseEdition);

        when(studentSubmissionRepo.findById(submissionId))
                .thenReturn(Optional.of(studentSubmission));

        when(courseEditionRepo.save(any()))
                .thenReturn(courseEdition);

        //when
        sut.acceptSubmission(submissionId);

        //then

        assertThat(student.getCoursesEditions()).hasSize(1);
        assertThat(courseEdition.getStudents()).hasSize(1);
        verify(courseEditionRepo, times(1)).save(any());
        verify(studentSubmissionRepo, times(1))
                .deleteById(submissionId);
    }

    @Test
    @DisplayName("Should throw StudentSubmissionNotFoundException.")
    void acceptSubmissionException() {
        //given
        long submissionId = 1L;
        when(studentSubmissionRepo.findById(submissionId))
                .thenReturn(Optional.empty());
        //when, then

        assertThrows(StudentSubmissionNotFoundException.class,
                () -> sut.acceptSubmission(submissionId));
    }
}