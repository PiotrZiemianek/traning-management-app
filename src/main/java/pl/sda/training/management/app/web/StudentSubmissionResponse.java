package pl.sda.training.management.app.web;

import lombok.Getter;
import pl.sda.training.management.app.domain.model.StudentSubmission;

@Getter
public class StudentSubmissionResponse {
    private final Long id;

    private final String submissionDate;


    private final String studentFullName;


    private final String courseEdition;

    private StudentSubmissionResponse(StudentSubmission studentSubmission) {
        this.id = studentSubmission.getId();

        this.submissionDate = studentSubmission.getSubmissionDate().toString();

        this.studentFullName = studentSubmission
                .getStudent()
                .getUser()
                .getFullName();

        this.courseEdition = studentSubmission
                .getCourseEdition()
                .getEditionCode()
                .value();
    }

    static StudentSubmissionResponse of(StudentSubmission studentSubmission) {
        return new StudentSubmissionResponse(studentSubmission);
    }
}
