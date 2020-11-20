package pl.sda.training.management.app.utils.dev;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.sda.training.management.app.domain.model.*;
import pl.sda.training.management.app.domain.repository.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class DataInitializer implements ApplicationRunner {
    private final StudentRepo studentRepo;
    private final StudentSubmissionRepo studentSubmissionRepo;
    private final UserRepo userRepo;
    private final CourseEditionRepo courseEditionRepo;
    private final CourseRepo courseRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user1 = User.builder()
                .login(Login.of("robot"))
                .password(Password.of("{bcrypt}$2a$10$Kmu5xlBT/v8pX3R1OFQwkOvuhIjgi4I0vVThPsQo7vsRlQ1230F66"))
                .firstName(FirstName.of("Piotr"))
                .lastName(LastName.of("Ziemianek"))
                .roles(List.of(UserRole.ROLE_PARTICIPANT))
                .isActive(true)
                .build();

        User user2 = User.builder()
                .login(Login.of("trainer"))
                .password(Password.of("{bcrypt}$2a$10$Kmu5xlBT/v8pX3R1OFQwkOvuhIjgi4I0vVThPsQo7vsRlQ1230F66"))
                .firstName(FirstName.of("Krzysztof"))
                .lastName(LastName.of("Krawczyk"))
                .roles(List.of(UserRole.ROLE_TRAINER))
                .isActive(true)
                .build();
        Trainer trainer = new Trainer(user2);


        Lesson lesson1 = new Lesson(LessonSubject.of("SQL"));
        Lesson lesson2 = new Lesson(LessonSubject.of("MongoDB"));
        LessonsBlock lessonsBlock1 = new LessonsBlock(BlockName.of("Bazy danych"));
        lesson1.setLessonsBlock(lessonsBlock1);
        lessonsBlock1.getLessons().add(lesson1);
        lesson2.setLessonsBlock(lessonsBlock1);
        lessonsBlock1.getLessons().add(lesson2);

        Lesson lesson3 = new Lesson(LessonSubject.of("SpringMVC"));
        Lesson lesson4 = new Lesson(LessonSubject.of("SpringData"));
        LessonsBlock lessonsBlock2 = new LessonsBlock(BlockName.of("Spring framework"));
        lesson3.setLessonsBlock(lessonsBlock2);
        lessonsBlock2.getLessons().add(lesson3);
        lesson4.setLessonsBlock(lessonsBlock2);
        lessonsBlock2.getLessons().add(lesson4);

        Course course = new Course(CourseName.of("Java od podstaw"));
        course.getLessonsBlocks().add(lessonsBlock1);
        lessonsBlock1.setCourse(course);
        course.getLessonsBlocks().add(lessonsBlock2);
        lessonsBlock2.setCourse(course);

        CourseEdition courseEdition = CourseEdition.builder()
                .editionCode(EditionCode.of("javaKTW22"))
                .trainers(new HashSet<>())
                .lessonsDetails(new ArrayList<>())
                .course(course)
                .students(new HashSet<>())
                .build();
        course.getLessonsBlocks().forEach(lessonsBlock -> lessonsBlock.getLessons()
                .stream()
                .map(LessonDetails::new)
                .forEach(lessonDetails -> {
                            trainer.getLessonDetails().add(lessonDetails);
                            lessonDetails.setTrainer(trainer);
                            lessonDetails.setLocalDateTime(LocalDateTime.now());
                            lessonDetails.setDuration(Duration.ZERO);
                            lessonDetails.setAddress(Address.of(
                                    City.of(""),
                                    StreetAddress.of(""),
                                    ZipCode.of(""),
                                    RoomNumber.of("")));
                            courseEdition.getLessonsDetails().add(lessonDetails);
                        }
                ));
        courseEdition.getTrainers().add(trainer);
        trainer.getCoursesList().add(courseEdition);
        Student student = new Student(null, user1, new ArrayList<>());
        StudentSubmission studentSubmission = new StudentSubmission();
        studentSubmission.setStudent(student);
        studentSubmission.setCourseEdition(courseEdition);

        courseRepo.save(course);
        userRepo.save(user2);
        courseEditionRepo.save(courseEdition);
        userRepo.save(user1);
        studentRepo.save(student);
        studentSubmissionRepo.save(studentSubmission);
    }
}
