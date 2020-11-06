package pl.sda.training.management.app.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.*;
import pl.sda.training.management.app.domain.service.CourseEditionService;
import pl.sda.training.management.app.domain.service.CourseService;
import pl.sda.training.management.app.domain.service.TrainerService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CourseEditionWebService {
    private final CourseEditionService courseEditionService;
    private final CourseService courseService;
    private final TrainerService trainerService;


    public void save(CourseEditionDTO editionDTO) {
        courseEditionService.save(dtoToCourseEdition(editionDTO));
    }

    public CourseEdition dtoToCourseEdition(CourseEditionDTO editionDTO) {
        CourseEdition courseEdition = courseEditionService
                .fromTemplate(courseService
                        .getById(editionDTO
                                .getCourseId()));

        courseEdition.setEditionCode(EditionCode.of(editionDTO.getEditionCode()));
        for (int i = 0; i < editionDTO.getLessonsDetails().size(); i++) {
            LessonDetails lessonDetails = courseEdition.getLessonsDetails().get(i);
            LessonDetailsDTO lessonDetailsDTO = editionDTO.getLessonsDetails().get(i);
            Trainer trainer = trainerService.getByLogin(Login.of(lessonDetailsDTO.getTrainerLogin()));

            courseEdition.getTrainers().add(trainer);

            trainer.getCoursesList().add(courseEdition);
            trainer.getLessonDetails().add(lessonDetails);
            trainer.getLessonsBlocks().add(lessonDetails.getLesson().getLessonsBlock());

            lessonDetails.setTrainer(trainer);
            lessonDetails.setLocalDateTime(LocalDateTime.parse(lessonDetailsDTO.getDateTime(),
                    DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
            lessonDetails.setDuration(Duration.ofMinutes(lessonDetailsDTO.getDuration()));
            lessonDetails.setAddress(Address.of(
                    City.of(lessonDetailsDTO.getCity()),
                    StreetAddress.of(lessonDetailsDTO.getStreet()),
                    ZipCode.of(lessonDetailsDTO.getZipCode()),
                    RoomNumber.of(lessonDetailsDTO.getRoomNumber())));
        }
        return courseEdition;
    }
}
