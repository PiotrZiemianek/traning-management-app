package pl.sda.training.management.app.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.Login;
import pl.sda.training.management.app.domain.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentWebService {
    private final StudentService studentService;


    public List<StudentToShow> getAll() {
        return studentService.findAll()
                .stream()
                .map(StudentToShow::of)
                .collect(Collectors.toList());
    }

    public StudentToShow getStudentToShow(String login) {
        return StudentToShow.of(studentService
                .getByLogin(Login.of(login)));
    }

    public void deleteStudent(String login) {
        studentService.delete(Login.of(login));
    }
}
