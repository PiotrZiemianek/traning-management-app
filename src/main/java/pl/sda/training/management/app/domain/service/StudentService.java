package pl.sda.training.management.app.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.Login;
import pl.sda.training.management.app.domain.model.Student;
import pl.sda.training.management.app.domain.repository.StudentRepo;
import pl.sda.training.management.app.exception.StudentNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepo studentRepo;


    public Student getByLogin(Login studentLogin) {
        return studentRepo.findByUser_Login(studentLogin)
                .orElseThrow(() -> new StudentNotFoundException("Student with login: " + studentLogin.value() + " not found."));
    }

    public boolean existsByLogin(Login studentLogin) {
        return studentRepo.existsByUser_Login(studentLogin);
    }

    public Student save(Student student) {
        return studentRepo.save(student);
    }

    public List<Student> findAll() {
        return studentRepo.findAll();
    }

    public synchronized void delete(Login login) {
        Student student = studentRepo.findByUser_Login(login)
                .orElseThrow(() -> new StudentNotFoundException("Student with login: " + login.value() + " not found."));
        studentRepo.delete(student);
    }
}
