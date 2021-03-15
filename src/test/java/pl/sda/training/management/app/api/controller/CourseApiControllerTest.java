package pl.sda.training.management.app.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import pl.sda.training.management.app.api.dto.CourseRequest;
import pl.sda.training.management.app.api.dto.CourseResourceAssembler;
import pl.sda.training.management.app.api.dto.LessonRequest;
import pl.sda.training.management.app.api.dto.LessonsBlockRequest;
import pl.sda.training.management.app.api.service.CourseApiService;
import pl.sda.training.management.app.domain.model.Course;
import pl.sda.training.management.app.domain.model.CourseName;
import pl.sda.training.management.app.security.WebSecurityTestConfig;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.sda.training.management.app.TestUtils.convertObjectToJsonBytes;
import static pl.sda.training.management.app.api.dto.CourseResource.COURSE_RESOURCE_ASSEMBLER;
import static pl.sda.training.management.app.utils.Constants.API_PRODUCES;

@SpringJUnitWebConfig(classes = WebSecurityTestConfig.class)
@WebMvcTest(CourseApiController.class)
@WithMockUser(roles = "ADMIN")
class CourseApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseApiService service;

    private CourseRequest dto;

    @BeforeEach
    public void setup() {
        CourseResourceAssembler assembler = COURSE_RESOURCE_ASSEMBLER;

        List<Course> courses = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Course course = new Course(CourseName.of("testCourseName" + i));
            course.setId((long) i);
            courses.add(course);
        }

        when(service.getCourses(any()))
                .thenReturn(assembler.toCollectionModel(courses));

        when(service.getCourse(1L))
                .thenReturn(assembler.toModel(courses.get(0)));

        LessonRequest lessonRequest = new LessonRequest(null, "testSubject");
        LessonsBlockRequest lessonsBlockRequest = new LessonsBlockRequest(null, "testBlock", List.of(lessonRequest));
        dto = new CourseRequest(null, "testCourse", List.of(lessonsBlockRequest));

        Course course = dto.toCourse();
        course.setId(1L);
        course.getLessonsBlocks().get(0).setId(1L);
        course.getLessonsBlocks().get(0).getLessons().get(0).setId(1L);

        when(service.save(dto))
                .thenReturn(assembler.toModel(course));

    }

    @Test
    @DisplayName("Should return default page of courses.")
    void getCourses() throws Exception {
        String url = linkTo(CourseApiController.class).toString();

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(API_PRODUCES));

        verify(service, times(1)).getCourses(PageRequest.of(0, 10));
    }

    @Test
    void getCourse() throws Exception {
        String url = linkTo(CourseApiController.class)
                .slash(1)
                .toString();

        mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(API_PRODUCES))
                .andExpect(jsonPath("$._links.self.href", endsWith(url)))
                .andExpect(jsonPath("$._links.courses.href",
                        endsWith(
                                linkTo(CourseApiController.class)
                                        .toString())));
    }

    @Test
    void postCourse() throws Exception {
        //given
        String url = linkTo(CourseApiController.class).toString();
        byte[] courseToSaveJson = convertObjectToJsonBytes(dto);

        //when, then
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(courseToSaveJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentType(API_PRODUCES))
                .andExpect(header()
                        .string("Location",
                                linkTo(CourseApiController.class)
                                        .slash(1)
                                        .toString()))
                .andExpect(jsonPath("$._links.self.href", endsWith("/1")))
                .andExpect(jsonPath("$._links.courses.href",
                        endsWith(
                                linkTo(CourseApiController.class)
                                        .toString())));
    }
}