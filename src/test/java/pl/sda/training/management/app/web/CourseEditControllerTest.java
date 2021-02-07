package pl.sda.training.management.app.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import pl.sda.training.management.app.security.WebSecurityTestConfig;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(classes = WebSecurityTestConfig.class)
@WebMvcTest(CourseEditController.class)
@WithUserDetails(value = "testAdmin")
class CourseEditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseWebService courseWebService;


    @Test
    void getCourseEdit() throws Exception {
        //given
        List<CourseToChoose> coursesToChoose = List.of(
                new CourseToChoose(1L, "Test1"),
                new CourseToChoose(2L, "Test2"));

        when(courseWebService.getAllCoursesToChoose())
                .thenReturn(coursesToChoose);

        //when, then
        mockMvc.perform(get("/admin/course-edit"))
                .andExpect(view().name("admin/course-edit-which"))
                .andExpect(model().attribute("coursesToChoose", coursesToChoose));
    }

    @Test
    void postIdWithId() throws Exception {
        //given
        CourseDTO courseDTO = new CourseDTO();
        when(courseWebService.getCourseById(1L))
                .thenReturn(courseDTO);

        //when, then
        mockMvc.perform((post("/admin/course-edit").param("chosenCourseId", "1")))
                .andExpect(view().name("admin/course-edit"))
                .andExpect(model().attribute("course", courseDTO));
    }

    @Test
    void postIdWithoutId() throws Exception {
        mockMvc.perform(post("/admin/course-edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/course-edit"));
    }

    @Test
    void saveCourse() throws Exception {
        mockMvc.perform(post("/admin/course-edit/save"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/course-edit"));

        verify(courseWebService, times(1))
                .save(any());
    }
}