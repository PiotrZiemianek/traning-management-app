package pl.sda.training.management.app.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import pl.sda.training.management.app.security.WebSecurityTestConfig;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(classes = WebSecurityTestConfig.class)
@WebMvcTest(AdminPanelController.class)
class AdminPanelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    List<StudentSubmissionResponse> submissionDTOs;
    List<StudentToShow> students;
    String studentLogin;
    List<String> editionCodes;

    @MockBean
    private StudentSubmissionWebService submissionWebService;
    @MockBean
    private StudentWebService studentWebService;
    @MockBean
    private CourseEditionWebService courseEditionWebService;

    @BeforeEach
    public void setup() {
        submissionDTOs = List.of(
                new StudentSubmissionResponse(
                        1L, "01.01.2020 12:00", "Andrzej Nowak", "testKTW01"),
                new StudentSubmissionResponse(
                        1L, "02.01.2020 12:00", "Marian Kowalski", "testKTW02")
        );
        when(submissionWebService.getSubmissions())
                .thenReturn(submissionDTOs);

        students = List.of(
                new StudentToShow("student1", "Andrzej", "Nowak", List.of("testKTW02")),
                new StudentToShow("student2", "Marian", "Kowalski", List.of("testKTW01"))
        );
        when(studentWebService.getAll())
                .thenReturn(students);
        when(studentWebService.getStudentToShow("student1"))
                .thenReturn(students.get(0));

        studentLogin = "student1";

        editionCodes = List.of("testKTW01", "testKTW02");
        when(courseEditionWebService.getEditionCodesWhereStudentIsNotParticipant(studentLogin))
                .thenReturn(editionCodes);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should redirect to accept-submissions when GET /admin.")
    void getAdminPanel() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("admin/accept-submissions"));
    }

    @Test
    @WithUserDetails(value = "testAdmin")
    @DisplayName("Should return view with student's submissions.")
    void acceptSubmissions() throws Exception {

        mockMvc.perform(get("/admin/accept-submissions"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/accept-submissions"))
                .andExpect(model().attribute("studentSubmissions", submissionDTOs));
    }

    @Test
    @WithUserDetails(value = "testAdmin")
    @DisplayName("Should return view with list of students.")
    void getStudents() throws Exception {
        mockMvc.perform(get("/admin/students"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/students"))
                .andExpect(model().attribute("students", students));
    }

    @Test
    @WithUserDetails(value = "testAdmin")
    void menageStudentWithLogin() throws Exception {
        mockMvc.perform(post("/admin/students").param("student", studentLogin))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/student"))
                .andExpect(model().attribute("student", students.get(0)))
                .andExpect(model().attribute("editionCodes", editionCodes));
    }

    @Test
    @WithUserDetails(value = "testAdmin")
    void menageStudentWithoutLogin() throws Exception {
        mockMvc.perform(post("/admin/students"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("students"));
    }

    @Test
    @WithUserDetails(value = "testAdmin")
    void addOrDelStudentFromCourseEditionWithoutParams() throws Exception {
        mockMvc.perform(post("/admin/students/" + studentLogin))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/student"))
                .andExpect(model().attribute("student", students.get(0)))
                .andExpect(model().attribute("editionCodes", editionCodes));
    }

    @Test
    @WithUserDetails(value = "testAdmin")
    void addOrDelStudentFromCourseEditionWithDelParam() throws Exception {
        mockMvc.perform(post("/admin/students/" + studentLogin)
                .param("deleteFromEdition", editionCodes.get(0)))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/student"))
                .andExpect(model().attribute("student", students.get(0)))
                .andExpect(model().attribute("editionCodes", editionCodes));
        verify(courseEditionWebService, times(1))
                .deleteStudentFromEdition(studentLogin, editionCodes.get(0));
    }
 @Test
    @WithUserDetails(value = "testAdmin")
    void addOrDelStudentFromCourseEditionWithAddParam() throws Exception {
        mockMvc.perform(post("/admin/students/" + studentLogin)
                .param("addToEdition", editionCodes.get(0)))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/student"))
                .andExpect(model().attribute("student", students.get(0)))
                .andExpect(model().attribute("editionCodes", editionCodes));
        verify(courseEditionWebService, times(1))
                .addStudentToEdition(studentLogin, editionCodes.get(0));
    }

    @Test
    @WithUserDetails(value = "testAdmin")
    void deleteStudent() throws Exception {

        mockMvc.perform(post("/admin/students/studentLogin/del"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/students"));

        verify(studentWebService, times(1)).deleteStudent("studentLogin");
    }
}