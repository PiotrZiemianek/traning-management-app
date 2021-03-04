package pl.sda.training.management.app.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import pl.sda.training.management.app.api.dto.LessonAssembler;
import pl.sda.training.management.app.api.dto.LessonResource;
import pl.sda.training.management.app.api.service.LessonApiService;
import pl.sda.training.management.app.domain.model.Lesson;
import pl.sda.training.management.app.domain.model.LessonSubject;
import pl.sda.training.management.app.domain.model.LessonsBlock;
import pl.sda.training.management.app.security.WebSecurityTestConfig;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.sda.training.management.app.utils.Constants.API_PRODUCES;

@SpringJUnitWebConfig(classes = WebSecurityTestConfig.class)
@WebMvcTest(LessonApiController.class)
@WithMockUser(roles = "ADMIN")
class LessonApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LessonApiService service;

    @BeforeEach
    public void setup() {

        LessonAssembler assembler = LessonResource.LESSON_ASSEMBLER;

        LessonsBlock lessonsBlock = new LessonsBlock();
        lessonsBlock.setId(1L);

        List<Lesson> lessons = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Lesson lesson = new Lesson(LessonSubject.of("testLessonSub"+i));
            lesson.setId((long) i);
            lesson.setLessonsBlock(lessonsBlock);
            lessons.add(lesson);
        }

        when(service.getLessons(any()))
                .thenReturn(assembler.toCollectionModel(lessons));

        when(service.getLesson(1L))
                .thenReturn(assembler.toModel(lessons.get(0)));
    }

    @Test
    void getLessons() throws Exception {
        String url = linkTo(LessonApiController.class).toString();

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(API_PRODUCES));

        verify(service, times(1)).getLessons(PageRequest.of(0, 10));
    }

    @Test
    void getLesson() throws Exception {
        String url = linkTo(LessonApiController.class)
                .slash(1)
                .toString();

        mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(API_PRODUCES))
                .andExpect(jsonPath("$._links.self.href", endsWith(url)))
                .andExpect(jsonPath("$._links.lessons-block.href",
                        endsWith(linkTo(LessonsBlockApiController.class)
                                .slash(1)
                                .toString())))
                .andExpect(jsonPath("$._links.lessons.href",
                        endsWith(
                                linkTo(LessonApiController.class)
                                        .toString())));

    }
}