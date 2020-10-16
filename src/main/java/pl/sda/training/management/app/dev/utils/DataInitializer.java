package pl.sda.training.management.app.dev.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.sda.training.management.app.domain.model.*;
import pl.sda.training.management.app.domain.repository.CourseEditionRepo;
import pl.sda.training.management.app.domain.repository.StudentRepo;
import pl.sda.training.management.app.domain.repository.StudentSubmissionRepo;
import pl.sda.training.management.app.domain.repository.UserRepo;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class DataInitializer implements ApplicationRunner {
    private final StudentRepo studentRepo;
    private final StudentSubmissionRepo studentSubmissionRepo;
    private final UserRepo userRepo;
    private final CourseEditionRepo courseEditionRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user1 = User.builder()
                .firstName(FirstName.of("Piotr"))
                .lastName(LastName.of("Ziemianek"))
                .roles(List.of(UserRole.ROLE_PARTICIPANT))
                .isActive(true)
                .build();
        CourseEdition courseEdition = new CourseEdition();
        courseEdition.setEditionCode(EditionCode.of("javaKTW22"));
        Student student = new Student(null, user1, new ArrayList<>());
        StudentSubmission studentSubmission = new StudentSubmission();
        studentSubmission.setStudent(student);
        studentSubmission.setCourseEdition(courseEdition);

        courseEditionRepo.save(courseEdition);
        userRepo.save(user1);
        studentRepo.save(student);
        studentSubmissionRepo.save(studentSubmission);
    }
}