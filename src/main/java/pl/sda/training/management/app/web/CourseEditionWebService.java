package pl.sda.training.management.app.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.config.DateTimeFormat;
import pl.sda.training.management.app.domain.model.*;
import pl.sda.training.management.app.domain.service.CourseEditionService;
import pl.sda.training.management.app.domain.service.CourseService;
import pl.sda.training.management.app.domain.service.TrainerService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ofPattern;

@Service
@RequiredArgsConstructor
public class CourseEditionWebService {
    private final CourseEditionService courseEditionService;
    private final CourseService courseService;
    private final TrainerService trainerService;
    private final DateTimeFormat dateTimeFormat;


    public void save(CourseEditionDTO editionDTO) {
        courseEditionService.save(dtoToCourseEdition(editionDTO));
    }

    private CourseEdition dtoToCourseEdition(CourseEditionDTO editionDTO) {
        CourseEdition courseEdition;
        if (editionDTO.getId() == null) {
            //create new
            courseEdition = courseEditionService
                    .fromTemplate(courseService
                            .getById(editionDTO
                                    .getCourseId()));
        } else {
            //get already existing to update
            courseEdition = courseEditionService.getById(editionDTO.getId());
            List<String> trainersLogins = editionDTO.getLessonsDetails()
                    .stream()
                    .map(LessonDetailsDTO::getTrainerLogin)
                    .collect(Collectors.toList());

            //remove trainers from courseEdition
            courseEdition.getTrainers()
                    .stream()
                    .filter(trainer -> !trainersLogins.contains(trainer.getUser().getLogin().value()))
                    .forEach(trainer -> {
                        trainer.getCoursesList().remove(courseEdition);
                        courseEdition.getTrainers().remove(trainer);
                    });
        }

        courseEdition.setEditionCode(EditionCode.of(editionDTO.getEditionCode()));

        for (int i = 0; i < editionDTO.getLessonsDetails().size(); i++) {
            LessonDetails lessonDetails = courseEdition.getLessonsDetails().get(i);
            LessonDetailsDTO lessonDetailsDTO = editionDTO.getLessonsDetails().get(i);
            Trainer trainer = trainerService.getByLogin(Login.of(lessonDetailsDTO.getTrainerLogin()));

            courseEdition.getTrainers().add(trainer);

            trainer.getCoursesList().add(courseEdition);
            trainer.getLessonDetails().add(lessonDetails);

            lessonDetails.setTrainer(trainer);
            lessonDetails.setLocalDateTime(LocalDateTime.parse(lessonDetailsDTO.getDateTime(),
                    ofPattern(dateTimeFormat.getFormat())));
            lessonDetails.setDuration(Duration.ofMinutes(lessonDetailsDTO.getDuration()));
            lessonDetails.setAddress(Address.of(
                    City.of(lessonDetailsDTO.getCity()),
                    StreetAddress.of(lessonDetailsDTO.getStreet()),
                    ZipCode.of(lessonDetailsDTO.getZipCode()),
                    RoomNumber.of(lessonDetailsDTO.getRoomNumber())));
        }
        return courseEdition;
    }

    public List<CourseEditionToChoose> getAllCoursesEditionsToChoose() {
        return courseEditionsToChooseOf(courseEditionService.getAll());
    }

    public List<CourseEditionToChoose> getAllEditionsToChooseByTrainerLogin(String login) {
        return courseEditionsToChooseOf(
                trainerService
                        .getByLogin(Login.of(login))
                        .getCoursesList());
    }

    public CourseEditionDTO getCourseEditionById(Long chosenCourseEditionId) {
        return CourseEditionDTO.of(courseEditionService.getById(chosenCourseEditionId));
    }

    private List<CourseEditionToChoose> courseEditionsToChooseOf(Collection<CourseEdition> courseEditions) {
        return courseEditions
                .stream()
                .map(CourseEditionToChoose::of)
                .collect(Collectors.toList());
    }
}
