package pl.sda.training.management.app.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sda.training.management.app.config.DateTimeFormat;
import pl.sda.training.management.app.domain.model.*;
import pl.sda.training.management.app.domain.service.CourseEditionService;
import pl.sda.training.management.app.domain.service.CourseService;
import pl.sda.training.management.app.domain.service.TrainerService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseEditionWebServiceTest {
    private CourseEditionWebService sut;

    @Mock
    private CourseEditionService courseEditionService;
    @Mock
    private CourseService courseService;
    @Mock
    private TrainerService trainerService;

    private final DateTimeFormat dateTimeFormat = new DateTimeFormat();

    @BeforeEach
    void setup() {
        sut = new CourseEditionWebService(courseEditionService, courseService, trainerService, dateTimeFormat);
    }

    @Test
    @DisplayName("Should return new CourseEdition based on data from DTO")
    void dtoToNewCourseEdition() {
        //given


        String trainerLogin = "login";
        LessonDetailsDTO details1DTO = LessonDetailsDTO.builder()
                .zipCode("00-000")
                .city("city")
                .street("street 1/1")
                .roomNumber("1")
                .duration(450)
                .dateTime("01.01.2000 12:00")
                .lessonId(1L)
                .trainerLogin(trainerLogin)
                .build();
        when(trainerService.getByLogin(Login.of(trainerLogin)))
                .thenReturn(new Trainer(User.builder()
                        .login(Login.of(trainerLogin)).build()));

        String trainerLogin1 = "login1";
        LessonDetailsDTO details2DTO = LessonDetailsDTO.builder()
                .zipCode("00-001")
                .city("town")
                .street("street 1/2")
                .roomNumber("2")
                .duration(360)
                .dateTime("02.01.2000 12:00")
                .lessonId(2L)
                .trainerLogin(trainerLogin1)
                .build();
        when(trainerService.getByLogin(Login.of(trainerLogin1)))
                .thenReturn(new Trainer(User.builder()
                        .login(Login.of(trainerLogin1)).build()));

        CourseEditionDTO editionDTO = CourseEditionDTO.builder()
                .editionCode("testTEST00")
                .courseId(1L)
                .lessonsDetails(new ArrayList<>())
                .build();
        editionDTO.getLessonsDetails().add(details1DTO);
        editionDTO.getLessonsDetails().add(details2DTO);

        CourseEdition courseEdition = new CourseEdition();
        courseEdition.getLessonsDetails().add(new LessonDetails());
        courseEdition.getLessonsDetails().add(new LessonDetails());

        when(courseEditionService.fromTemplate(any())).thenReturn(courseEdition);

        ArgumentCaptor<CourseEdition> editionArgumentCaptor = ArgumentCaptor.forClass(CourseEdition.class);

        //when
        sut.save(editionDTO);

        //then

        verify(courseEditionService).save(editionArgumentCaptor.capture());

        assertThat(editionArgumentCaptor.getValue().getEditionCode().value()).isEqualTo(editionDTO.getEditionCode());

        assertThat(editionArgumentCaptor.getValue().getTrainers().size())
                .isEqualTo(editionDTO.getLessonsDetails()
                        .stream()
                        .map(LessonDetailsDTO::getTrainerLogin)
                        .distinct()
                        .count());

        assertThat(editionArgumentCaptor.getValue().getLessonsDetails().size())
                .isEqualTo(editionDTO.getLessonsDetails().size());

        for (int i = 0; i < editionArgumentCaptor.getValue().getLessonsDetails().size(); i++) {
            LessonDetails details = editionArgumentCaptor.getValue().getLessonsDetails().get(i);
            LessonDetailsDTO detailsDTO = editionDTO.getLessonsDetails().get(i);

            assertThat(details.getTrainer().getUser().getLogin().value())
                    .isEqualTo(detailsDTO.getTrainerLogin());

            assertThat(details.getLocalDateTime())
                    .isEqualTo(LocalDateTime.parse(detailsDTO.getDateTime(),
                            ofPattern(dateTimeFormat.getFormat())));

            assertThat(details.getDuration())
                    .isEqualTo(Duration.ofMinutes(detailsDTO.getDuration()));

            assertThat(details.getAddress().getCity().value())
                    .isEqualTo(detailsDTO.getCity());

            assertThat(details.getAddress().getStreetAddress().value())
                    .isEqualTo(detailsDTO.getStreet());

            assertThat(details.getAddress().getZipCode().value())
                    .isEqualTo(detailsDTO.getZipCode());

            assertThat(details.getAddress().getRoomNumber().value())
                    .isEqualTo(detailsDTO.getRoomNumber());

        }
    }
}