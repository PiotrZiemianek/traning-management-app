package pl.sda.training.management.app.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import pl.sda.training.management.app.api.controller.CourseApiController;
import pl.sda.training.management.app.api.dto.CourseResource;
import pl.sda.training.management.app.domain.model.Course;
import pl.sda.training.management.app.domain.model.CourseName;
import pl.sda.training.management.app.domain.service.CourseService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static pl.sda.training.management.app.TestUtils.getPage;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CourseApiServiceTest {
    @Autowired
    private CourseApiService sut;

    @MockBean
    private CourseService courseService;


    @BeforeEach
    public void setup() {

        List<Course> courses = new ArrayList<>();
        for (int i = 1; i <= 35; i++) {
            Course course = new Course(CourseName.of("testCourseName" + i));
            course.setId((long) i);
            courses.add(course);
        }

        int page = 1;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);
        when(courseService.getPage(pageRequest))
                .thenReturn(new PageImpl<>(getPage(courses, page, size), pageRequest, courses.size()));

        when(courseService.getById(1L))
                .thenReturn(courses.get(0));

    }

    @Test
    void getCourses() {
        //given
        int page = 1;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);

        //when
        CollectionModel<CourseResource> courses = sut.getCourses(pageRequest);

        //then
        System.out.println(courses);
        assertThat(courses.getContent()).hasSize(size);
        assertThat(courses.getLinks().getLink("self")).isPresent();
        assertThat(courses.getLinks().getLink("courses")).isPresent();
        assertThat(courses.getLinks().getLink("first")).isPresent();
        assertThat(courses.getLinks().getLink("last")).isPresent();
        assertThat(courses.getLinks().getLink("next")).isPresent();
        assertThat(courses.getLinks().getLink("prev")).isPresent();

    }

    @Test
    void getCourse() {
        //given
        long id = 1L;

        //when
        CourseResource course = sut.getCourse(id);

        //then
        assertThat(course.getLink("self")).isPresent();
        assertThat(course.getLink("self").get().getHref())
                .endsWith(linkTo(CourseApiController.class)
                        .slash(1)
                        .toString());
    }
}
