package pl.sda.training.management.app.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.EditionCode;
import pl.sda.training.management.app.domain.model.Login;
import pl.sda.training.management.app.domain.model.Trainer;
import pl.sda.training.management.app.domain.service.StudentService;
import pl.sda.training.management.app.domain.service.TrainerService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonsBlockWebService {
    private final TrainerService trainerService;
    private final StudentService studentService;

    public Set<LessonsBlockDTO> getAllByTrainerLoginAndEditionCode(String trainerLogin, String editionCode) {
        return trainerService.getByLogin(Login.of(trainerLogin)).getLessonDetails()
                .stream()
                .filter(lessonDetails -> lessonDetails.getCourseEdition()
                        .getEditionCode().equals(EditionCode.of(editionCode)))
                .map(lessonDetails -> LessonsBlockDTO.of(lessonDetails.getLesson().getLessonsBlock()))
                .collect(Collectors.toSet());
    }

    public Map<String, Set<LessonsBlockDTO>> getAllByTrainerLoginOrderedByEditionCode(String trainerLogin) {
        Map<String, Set<LessonsBlockDTO>> lessonsBlockMap = new HashMap<>();

        Trainer trainer = trainerService.getByLogin(Login.of(trainerLogin));

        trainer.getCoursesList().forEach(courseEdition -> lessonsBlockMap.put(
                courseEdition.getEditionCode().value(),
                new HashSet<>()));

        trainer.getLessonDetails()
                .forEach(lessonDetails -> lessonsBlockMap
                        .get(lessonDetails.getCourseEdition().getEditionCode().value())
                        .add(LessonsBlockDTO.of(lessonDetails.getLesson().getLessonsBlock())));
        return lessonsBlockMap;
    }

    public Map<String, Set<LessonsBlockDTO>> getAllByStudentLoginOrderedByEditionCode(String studentLogin) {

        return studentService.getByLogin(Login.of(studentLogin))
                .getCoursesEditions()
                .stream()
                .collect(Collectors.toMap(
                        courseEdition -> courseEdition.getEditionCode().value(),
                        courseEdition -> courseEdition.getCourse().getLessonsBlocks()
                                .stream().map(LessonsBlockDTO::of)
                                .collect(Collectors.toSet()), (a, b) -> b));


    }
}
