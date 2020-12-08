package pl.sda.training.management.app.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.CourseEdition;
import pl.sda.training.management.app.domain.model.EditionCode;
import pl.sda.training.management.app.domain.model.Login;
import pl.sda.training.management.app.domain.model.StudentSubmission;
import pl.sda.training.management.app.domain.repository.CourseEditionRepo;
import pl.sda.training.management.app.domain.repository.StudentRepo;
import pl.sda.training.management.app.domain.repository.StudentSubmissionRepo;
import pl.sda.training.management.app.exception.CourseEditionNotFoundException;
import pl.sda.training.management.app.exception.StudentNotFoundException;
import pl.sda.training.management.app.exception.StudentSubmissionNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentSubmissionService {
    private final StudentSubmissionRepo studentSubmissionRepo;
    private final CourseEditionRepo courseEditionRepo;
    private final StudentRepo studentRepo;

    public StudentSubmission save(StudentSubmission studentSubmission) {
        return studentSubmissionRepo.save(studentSubmission);
    }

    public void acceptSubmission(Long submissionId) {
        StudentSubmission submission = getSubmissionFromDB(submissionId);
        CourseEdition courseEdition = submission.getCourseEdition();

        //add student to course edition.
        courseEdition
                .getStudents()
                .add(submission.getStudent());

        //add course to student
        submission.getStudent()
                .getCoursesEditions()
                .add(submission.getCourseEdition());

        courseEditionRepo.save(courseEdition);
        studentSubmissionRepo.deleteById(submissionId);
    }

    private StudentSubmission getSubmissionFromDB(Long submissionId) {
        Optional<StudentSubmission> optionalStudentSubmission = studentSubmissionRepo.findById(submissionId);
        if (optionalStudentSubmission.isPresent()) {
            return optionalStudentSubmission.get();
        }
        throw new StudentSubmissionNotFoundException("StudentSubmission with id: " + submissionId + " not found.");
    }

    public List<StudentSubmission> getAll() {
        return studentSubmissionRepo.findAll();
    }

    public void delete(Long id) {
        studentSubmissionRepo.deleteById(id);
    }

    public void saveSubmission(EditionCode code, Login studentLogin) {

        studentSubmissionRepo.save(new StudentSubmission(

                studentRepo.findByUser_Login(studentLogin)
                        .orElseThrow(() -> new StudentNotFoundException(
                                "Student with login: " + studentLogin.value() + " not found.")),

                courseEditionRepo.findByEditionCode(code)
                        .orElseThrow(() -> new CourseEditionNotFoundException(
                                "CourseEdition with editionCode: " + code.value() + " not found."))));

    }

    public boolean existsByEditionCodeAndStudentLogin(EditionCode code, Login studentLogin) {
        return studentSubmissionRepo.existsByCourseEdition_EditionCodeAndStudent_User_Login(code, studentLogin);
    }
}
