package pl.sda.training.management.app.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.Login;
import pl.sda.training.management.app.domain.model.Student;
import pl.sda.training.management.app.domain.repository.StudentRepo;
import pl.sda.training.management.app.exception.StudentNotFoundException;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepo studentRepo;


    public Student getByLogin(Login studentLogin) {
        return studentRepo.findByUser_Login(studentLogin)
                .orElseThrow(() -> new StudentNotFoundException("Student with login: " + studentLogin.value() + " not found."));
    }
}
