package pl.sda.training.management.app.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import pl.sda.training.management.app.domain.model.Course;
import pl.sda.training.management.app.domain.model.CourseName;
import pl.sda.training.management.app.domain.service.CourseService;
import pl.sda.training.management.app.security.WebSecurityTestConfig;
import pl.sda.training.management.app.utils.Constants;

import java.util.List;

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(classes = WebSecurityTestConfig.class)
@WebMvcTest(CourseApiController.class)
@WithMockUser(roles = "ADMIN")
class CourseApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private List<Course> courses;

    @MockBean
    private CourseService courseService;

    @BeforeEach
    public void setup() {
        Course course1 = new Course(CourseName.of("testCourseName1"));
        course1.setId(1L);
        Course course2 = new Course(CourseName.of("testCourseName2"));
        course2.setId(2L);

        courses = List.of(course1, course2);

        when(courseService.findAll()).thenReturn(courses);
        when(courseService.getById(1L)).thenReturn(courses.get(0));

    }

    @Test
    void getCourses() throws Exception {
        String url = "/api/course";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(Constants.API_PRODUCES))
                .andExpect(jsonPath("$._links.courses.href", endsWith(url)))
                .andDo(print());
    }

    @Test
    void getCourse() throws Exception {
        String url = "/api/course/1";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(Constants.API_PRODUCES))
                .andExpect(jsonPath("$.courseName").value(courses.get(0).getName().value()))
                .andExpect(jsonPath("$.lessonsBlocks").isEmpty())
                .andExpect(jsonPath("$._links.self.href", endsWith(url)))
                .andDo(print());
    }
}