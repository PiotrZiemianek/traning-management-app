package pl.sda.training.management.app.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.EditionCode;
import pl.sda.training.management.app.domain.model.Login;
import pl.sda.training.management.app.domain.model.Student;
import pl.sda.training.management.app.domain.model.User;
import pl.sda.training.management.app.domain.service.StudentService;
import pl.sda.training.management.app.domain.service.StudentSubmissionService;
import pl.sda.training.management.app.domain.service.UserService;
import pl.sda.training.management.app.security.PrincipalRefresher;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentSubmissionWebService {
    private final StudentSubmissionService submissionService;
    private final StudentService studentService;
    private final UserService userService;

    public List<StudentSubmissionResponse> getSubmissions() {
        return submissionService.getAll().stream()
                .map(StudentSubmissionResponse::of)
                .collect(Collectors.toList());
    }

    public void acceptSubmissionById(Long id) {
        submissionService.acceptSubmission(id);
    }

    public void deleteSubmissionById(Long id) {
        submissionService.delete(id);
    }

    public void sendSubmission(String editionCode, String login) {

        EditionCode code = EditionCode.of(editionCode);
        Login studentLogin = Login.of(login);

        //user becomes student
        if (!studentService.existsByLogin(studentLogin)) {
            User user = userService.getUserByLogin(studentLogin);
            studentService.save(new Student(user));
            PrincipalRefresher.refreshPrincipal(user);
        }

        if (!submissionService.existsByEditionCodeAndStudentLogin(code, studentLogin)) {
            submissionService.saveSubmission(code, studentLogin);
        }
    }
}
