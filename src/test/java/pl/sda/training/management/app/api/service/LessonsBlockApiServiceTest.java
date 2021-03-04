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
import pl.sda.training.management.app.api.controller.LessonsBlockApiController;
import pl.sda.training.management.app.api.dto.LessonsBlockResource;
import pl.sda.training.management.app.domain.model.BlockName;
import pl.sda.training.management.app.domain.model.Course;
import pl.sda.training.management.app.domain.model.LessonsBlock;
import pl.sda.training.management.app.domain.service.LessonsBlockService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static pl.sda.training.management.app.TestUtils.getPage;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class LessonsBlockApiServiceTest {

    @Autowired
    private LessonsBlockApiService sut;

    @MockBean
    LessonsBlockService lessonsBlockService;

    @BeforeEach
    public void setup() {
        Course course = new Course();
        course.setId(1L);

        List<LessonsBlock> blocks = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            LessonsBlock lessonsBlock = new LessonsBlock(BlockName.of("testBlockName" + i));
            lessonsBlock.setId((long) i);
            lessonsBlock.setCourse(course);
            blocks.add(lessonsBlock);
        }

        int page = 1;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);

        when(lessonsBlockService.getPage(pageRequest))
                .thenReturn(new PageImpl<>(getPage(blocks, page, size), pageRequest, blocks.size()));

        when(lessonsBlockService.getById(1L))
                .thenReturn(blocks.get(0));
    }

    @Test
    void getBlocks() {
        //given
        int page = 1;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);

        //when
        CollectionModel<LessonsBlockResource> blocks = sut.getBlocks(pageRequest);

        //then
        System.out.println(blocks);
        assertThat(blocks.getContent()).hasSize(size);
        assertThat(blocks.getLinks().getLink("self")).isPresent();
        assertThat(blocks.getLinks().getLink("lessons-blocks")).isPresent();
        assertThat(blocks.getLinks().getLink("first")).isPresent();
        assertThat(blocks.getLinks().getLink("last")).isPresent();
        assertThat(blocks.getLinks().getLink("next")).isPresent();
        assertThat(blocks.getLinks().getLink("prev")).isPresent();

    }

    @Test
    void getLessonBlock() {
        //given
        long id = 1L;

        //when
        LessonsBlockResource lessonBlock = sut.getLessonBlock(id);

        //then
        assertThat(lessonBlock.getLink("self")).isPresent();
        assertThat(lessonBlock.getLink("self").get().getHref())
                .endsWith(linkTo(LessonsBlockApiController.class)
                        .slash(1)
                        .toString());

        assertThat(lessonBlock.getLink("course")).isPresent();
        assertThat(lessonBlock.getLink("course").get().getHref())
                .endsWith(linkTo(CourseApiController.class)
                        .slash(1)
                        .toString());
    }
}