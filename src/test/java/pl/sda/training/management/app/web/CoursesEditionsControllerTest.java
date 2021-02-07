package pl.sda.training.management.app.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import pl.sda.training.management.app.domain.model.EditionCode;
import pl.sda.training.management.app.domain.service.CourseEditionService;
import pl.sda.training.management.app.security.WebSecurityTestConfig;
import pl.sda.training.management.app.utils.Counter;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(classes = WebSecurityTestConfig.class)
@WebMvcTest(CoursesEditionsController.class)
@WithUserDetails(value = "testAdmin")
class CoursesEditionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private List<CourseToChoose> coursesToChoose;

    private CourseDTO courseDTO;

    private List<TrainerToShow> trainersToShow;

    private List<CourseEditionToChoose> editionsToChoose;

    private CourseEditionDTO courseEditionDTO;

    @MockBean
    private CourseWebService courseWebService;
    @MockBean
    private TrainerWebService trainerWebService;
    @MockBean
    private CourseEditionWebService courseEditionWebService;
    @MockBean
    private CourseEditionService courseEditionService;


    @BeforeEach
    public void setup() {
        coursesToChoose = List.of(
                new CourseToChoose(1L, "Test1"),
                new CourseToChoose(2L, "Test2"));

        when(courseWebService.getAllCoursesToChoose())
                .thenReturn(coursesToChoose);

        courseDTO = new CourseDTO(1L, "Test1");

        when(courseWebService.getCourseById(1L))
                .thenReturn(courseDTO);

        trainersToShow = List.of(
                new TrainerToShow("testLogin1", "testName1", "testSurname1", new ArrayList<>()),
                new TrainerToShow("testLogin2", "testName2", "testSurname2", new ArrayList<>()));

        when(trainerWebService.getTrainersToShow())
                .thenReturn(trainersToShow);

        editionsToChoose = List.of(
                new CourseEditionToChoose(1L, "testKTW01", "test1"),
                new CourseEditionToChoose(2L, "testKTW02", "test2"));

        when(courseEditionWebService.getAllCoursesEditionsToChoose())
                .thenReturn(editionsToChoose);

        courseEditionDTO = CourseEditionDTO.builder()
                .courseId(1L)
                .editionCode("testKTW01")
                .lessonsDetails(new ArrayList<>())
                .build();
    }

    @Test
    void getNewCourseEdition() throws Exception {
        mockMvc.perform(get("/admin/courses-editions/new"))
                .andExpect(view().name("admin/course-edition-new"))
                .andExpect(model().attribute("coursesToChoose", coursesToChoose));
    }

    @Test
    void postCourseIdWithoutId() throws Exception {
        mockMvc.perform(post("/admin/courses-editions/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/course-wizard"));
    }

    @Test
    void postCourseId() throws Exception {
        mockMvc.perform(post("/admin/courses-editions/new")
                .param("chosenCourseId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/course-edition-new-dates"))
                .andExpect(model().attribute("course", courseDTO))
                .andExpect(model().attribute("courseEdition", new CourseEditionDTO(courseDTO)))
                .andExpect(model().attribute("counter", new Counter()))
                .andExpect(model().attribute("trainers", trainersToShow));
    }

    @Test
    void saveNewCourseEdition() throws Exception {

        mockMvc.perform(post("/admin/courses-editions/save")
                .flashAttr("courseEdition", courseEditionDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/courses-editions"));

        verify(courseEditionWebService, times(1)).save(courseEditionDTO);
    }

    @Test
    void saveNewCourseEditionWithValidationErrors() throws Exception {
        //given
        CourseEditionDTO badCourseEditionDTO = CourseEditionDTO.builder()
                .courseId(1L)
                .editionCode("badCode")
                .lessonsDetails(new ArrayList<>())
                .build();

        //when, then
        mockMvc.perform(post("/admin/courses-editions/save")
                .flashAttr("courseEdition", badCourseEditionDTO))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/course-edition-new-dates"));
    }

    @Test
    void getEditCourseEdition() throws Exception {
        mockMvc.perform(get("/admin/courses-editions/edit"))
                .andExpect(view().name("admin/course-edition-edit"))
                .andExpect(model().attribute("editionsToChoose", editionsToChoose));
    }

    @Test
    void postEditionId() throws Exception {
        //given
        CourseEditionDTO editionDTO = CourseEditionDTO.builder()
                .id(1L)
                .editionCode("testKTW01")
                .courseId(1L)
                .lessonsDetails(new ArrayList<>())
                .build();
        when(courseEditionWebService.getCourseEditionById(1L))
                .thenReturn(editionDTO);

        //when, then
        mockMvc.perform(post("/admin/courses-editions/edit")
                .param("chosenCourseEditionId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/course-edition-edit-dates"))
                .andExpect(model().attribute("course", courseDTO))
                .andExpect(model().attribute("trainers", trainersToShow))
                .andExpect(model().attribute("courseEdition", editionDTO))
                .andExpect(model().attribute("counter", new Counter()));
    }

    @Test
    void postEditionIdWithoutId() throws Exception {
        mockMvc.perform(post("/admin/courses-editions/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/course-wizard"));
    }

    @Test
    void saveEditedCourseEdition() throws Exception {

        mockMvc.perform(post("/admin/courses-editions/save")
                .flashAttr("courseEdition", courseEditionDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/courses-editions"));

        verify(courseEditionWebService, times(1)).save(courseEditionDTO);

    }

    @Test
    void saveEditedCourseEditionWithValidationErrors() throws Exception {
        //given
        when(courseEditionService.existsByEditionCode(EditionCode.of(courseEditionDTO.getEditionCode())))
                .thenReturn(true);

        //when, then
        mockMvc.perform(post("/admin/courses-editions/save")
                .flashAttr("courseEdition", courseEditionDTO))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/course-edition-new-dates"));
    }
}