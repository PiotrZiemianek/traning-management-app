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
import pl.sda.training.management.app.domain.service.StudentService;
import pl.sda.training.management.app.domain.service.TrainerService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @Mock
    StudentService studentService;

    private final DateTimeFormat dateTimeFormat = new DateTimeFormat();

    @BeforeEach
    void setup() {
        sut = new CourseEditionWebService(courseEditionService, courseService, trainerService, dateTimeFormat, studentService);
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

    @Test
    @DisplayName("Should return CourseEditionToChoose list from trainer courses list.")
    void getAllEditionsToChooseByTrainerLogin() {
        //given
        String login = "testLogin";
        Course testCourse = new Course(CourseName.of("testCourseName"));
        CourseEdition testEdition01 = CourseEdition.builder()
                .id(1L)
                .editionCode(EditionCode.of("testKTW01"))
                .course(testCourse)
                .build();
        CourseEdition testEdition02 = CourseEdition.builder()
                .id(2L)
                .editionCode(EditionCode.of("testKTW02"))
                .course(testCourse)
                .build();
        Trainer trainer = Trainer.builder()
                .coursesList(Set.of(testEdition01, testEdition02))
                .build();

        when(trainerService.getByLogin(Login.of(login)))
                .thenReturn(trainer);

        //when
        List<CourseEditionToChoose> result = sut.getAllEditionsToChooseByTrainerLogin(login);

        //then
        assertThat(trainer.getCoursesList().size()).isEqualTo(result.size());
        assertThat(result).contains(CourseEditionToChoose.of(testEdition01));
        assertThat(result).contains(CourseEditionToChoose.of(testEdition02));
    }

    @Test
    @DisplayName("Should return editions codes of Course where user is not edition participant.")
    void getEditionsCodesByCourseIdWhereUserNotParticipated() {
        //given
        Long courseId = 1L;
        String userLogin = "testLogin";
        Login studentLogin = Login.of(userLogin);
        List<EditionCode> codesWhereParticipated = List.of(EditionCode.of("testKTW01"), EditionCode.of("testKTW02"));

        when(studentService.existsByLogin(studentLogin)).thenReturn(true);

        when(courseEditionService.getEditionCodesByCourseIdWhereStudentNotParticipated(courseId, studentLogin))
                .thenReturn(codesWhereParticipated);

        //when
        List<String> result = sut.getEditionsCodesByCourseIdWhereUserNotParticipated(courseId, userLogin);

        //then
        verify(studentService, times(1)).existsByLogin(studentLogin);
        verify(courseEditionService, times(1)).getEditionCodesByCourseIdWhereStudentNotParticipated(courseId, studentLogin);
        assertThat(result.size()).isEqualTo(codesWhereParticipated.size());

        for (int i = 0; i < result.size(); i++) {
            assertThat(result.get(i)).isEqualTo(codesWhereParticipated.get(i).value());
        }
    }

    @Test
    @DisplayName("Should return all editions codes of Course because User is not the Student.")
    void getEditionsCodesByCourseIdWhereUserNotParticipated1() {
        //given
        Long courseId = 1L;
        String userLogin = "testLogin";
        Login studentLogin = Login.of(userLogin);
        List<EditionCode> allCodes = List.of(EditionCode.of("testKTW01"), EditionCode.of("testKTW02"), EditionCode.of("testKTW03"), EditionCode.of("testKTW04"));

        when(studentService.existsByLogin(Login.of(userLogin))).thenReturn(false);

        when(courseEditionService.getEditionCodesByCourseId(courseId))
                .thenReturn(allCodes);

        //when
        List<String> result = sut.getEditionsCodesByCourseIdWhereUserNotParticipated(courseId, userLogin);

        //then
        verify(studentService, times(1)).existsByLogin(studentLogin);
        verify(courseEditionService, times(1)).getEditionCodesByCourseId(courseId);
        assertThat(result.size()).isEqualTo(allCodes.size());

        for (int i = 0; i < result.size(); i++) {
            assertThat(result.get(i)).isEqualTo(allCodes.get(i).value());
        }
    }
}