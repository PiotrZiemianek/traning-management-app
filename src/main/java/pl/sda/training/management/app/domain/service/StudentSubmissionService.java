package pl.sda.training.management.app.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.CourseEdition;
import pl.sda.training.management.app.domain.model.StudentSubmission;
import pl.sda.training.management.app.domain.repository.CourseEditionRepo;
import pl.sda.training.management.app.domain.repository.StudentSubmissionRepo;
import pl.sda.training.management.app.exception.StudentSubmissionNotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentSubmissionService {
    private final StudentSubmissionRepo studentSubmissionRepo;
    private final CourseEditionRepo courseEditionRepo;

    public StudentSubmission save(StudentSubmission studentSubmission) {
        return studentSubmissionRepo.save(studentSubmission);
    }

    public void acceptSubmission(Long submissionId) {
        StudentSubmission submission = getSubmissionById(submissionId);
        CourseEdition courseEdition = submission.getCourseEdition();
        courseEdition.addStudent(submission.getStudent());
        courseEditionRepo.save(courseEdition);
        studentSubmissionRepo.deleteById(submissionId);
    }

    private StudentSubmission getSubmissionById(Long submissionId) {
        Optional<StudentSubmission> optionalStudentSubmission = studentSubmissionRepo.findById(submissionId);
        if (optionalStudentSubmission.isPresent()) {
            return optionalStudentSubmission.get();
        }
        throw new StudentSubmissionNotFoundException("StudentSubmission with id: " + submissionId + " not found.");
    }
}
