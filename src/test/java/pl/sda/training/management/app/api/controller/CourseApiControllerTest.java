package pl.sda.training.management.app.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import pl.sda.training.management.app.api.dto.CourseResourceAssembler;
import pl.sda.training.management.app.api.service.CourseApiService;
import pl.sda.training.management.app.domain.model.Course;
import pl.sda.training.management.app.domain.model.CourseName;
import pl.sda.training.management.app.security.WebSecurityTestConfig;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.sda.training.management.app.utils.Constants.API_PRODUCES;
import static pl.sda.training.management.app.utils.Constants.API_URL;

@SpringJUnitWebConfig(classes = WebSecurityTestConfig.class)
@WebMvcTest(CourseApiController.class)
@WithMockUser(roles = "ADMIN")
class CourseApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseApiService service;

    @BeforeEach
    public void setup() {
        CourseResourceAssembler assembler = new CourseResourceAssembler();

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

    }

    @Test
    @DisplayName("Should return default page of courses.")
    void getCourses() throws Exception {
        String url = API_URL + "/course";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(API_PRODUCES));
        verify(service,times(1)).getCourses(PageRequest.of(0,10));
    }

    @Test
    void getCourse() throws Exception {
        String url = API_URL + "/course/1";
        mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(API_PRODUCES))
                .andExpect(jsonPath("$._links.self.href", endsWith(url)));
    }
}