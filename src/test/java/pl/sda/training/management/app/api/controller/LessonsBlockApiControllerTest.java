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
import pl.sda.training.management.app.api.dto.LessonsBlockAssembler;
import pl.sda.training.management.app.api.dto.LessonsBlockResource;
import pl.sda.training.management.app.api.service.LessonsBlockApiService;
import pl.sda.training.management.app.domain.model.BlockName;
import pl.sda.training.management.app.domain.model.Course;
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
@WebMvcTest(LessonsBlockApiController.class)
@WithMockUser(roles = "ADMIN")
class LessonsBlockApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LessonsBlockApiService service;

    @BeforeEach
    public void setup() {

        LessonsBlockAssembler assembler = LessonsBlockResource.LESSONS_BLOCK_ASSEMBLER;

        Course course = new Course();
        course.setId(1L);

        List<LessonsBlock> blocks = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            LessonsBlock lessonsBlock = new LessonsBlock(BlockName.of("testBlockName" + i));
            lessonsBlock.setId((long) i);
            lessonsBlock.setCourse(course);
            blocks.add(lessonsBlock);
        }

        when(service.getBlocks(any()))
                .thenReturn(assembler.toCollectionModel(blocks));

        when(service.getLessonBlock(1L))
                .thenReturn(assembler.toModel(blocks.get(0)));
    }

    @Test
    void getBlocks() throws Exception {
        String url = linkTo(LessonsBlockApiController.class).toString();

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(API_PRODUCES));

        verify(service, times(1)).getBlocks(PageRequest.of(0, 10));
    }

    @Test
    void getLessonsBlock() throws Exception {
        String url = linkTo(LessonsBlockApiController.class)
                .slash(1)
                .toString();

        mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(API_PRODUCES))
                .andExpect(jsonPath("$._links.self.href", endsWith(url)))
                .andExpect(jsonPath("$._links.course.href",
                        endsWith(linkTo(CourseApiController.class)
                                .slash(1)
                                .toString())))
                .andExpect(jsonPath("$._links.lessons-block.href",
                        endsWith(
                                linkTo(LessonsBlockApiController.class)
                                        .toString())));

    }
}