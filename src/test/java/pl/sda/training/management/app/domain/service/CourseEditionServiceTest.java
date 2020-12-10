package pl.sda.training.management.app.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sda.training.management.app.domain.model.*;
import pl.sda.training.management.app.domain.repository.CourseEditionRepo;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CourseEditionServiceTest {
    private CourseEditionService sut;

    @Mock
    private CourseEditionRepo courseEditionRepo;
    @Mock
    private StudentService studentService;

    @BeforeEach
    void setup() {
        sut = new CourseEditionService(courseEditionRepo, studentService);
    }

    @Test
    @DisplayName("Should return CourseEdition based on Course template.")
    void fromTemplate() {
        //given
        Course course = new Course(CourseName.of("Test course"));
        course.setId(1L);

        LessonsBlock testBlock1 = new LessonsBlock(BlockName.of("testBlock1"));
        testBlock1.setId(1L);
        testBlock1.setCourse(course);

        Lesson lesson1_1 = new Lesson(LessonSubject.of("lesson1_1"), testBlock1);
        lesson1_1.setId(1L);

        Lesson lesson1_2 = new Lesson(LessonSubject.of("lesson1_2"), testBlock1);
        lesson1_2.setId(2L);

        LessonsBlock testBlock2 = new LessonsBlock(BlockName.of("testBlock2"));
        testBlock2.setId(2L);
        testBlock2.setCourse(course);

        Lesson lesson2_1 = new Lesson(LessonSubject.of("lesson2_1"), testBlock2);
        lesson2_1.setId(3L);

        Lesson lesson2_2 = new Lesson(LessonSubject.of("lesson2_2"), testBlock2);
        lesson2_2.setId(4L);

        testBlock1.getLessons().add(lesson1_1);
        testBlock1.getLessons().add(lesson1_2);

        testBlock2.getLessons().add(lesson2_1);
        testBlock2.getLessons().add(lesson2_2);

        course.getLessonsBlocks().add(testBlock1);
        course.getLessonsBlocks().add(testBlock2);
        //when
        CourseEdition courseEdition = sut.fromTemplate(course);

        //then
        assertThat(courseEdition.getCourse()).isEqualTo(course);
        assertThat(courseEdition.getStudents()).isEmpty();
        assertThat(courseEdition.getTrainers()).isEmpty();
        assertThat(courseEdition.getEditionCode()).isNull();

        assertThat(courseEdition.getLessonsDetails())
                .hasSize(course.getLessonsBlocks()
                        .stream()
                        .map(lessonsBlock -> lessonsBlock.getLessons().size())
                        .reduce(0,Integer::sum));


    }
}