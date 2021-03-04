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
import pl.sda.training.management.app.api.controller.LessonApiController;
import pl.sda.training.management.app.api.controller.LessonsBlockApiController;
import pl.sda.training.management.app.api.dto.LessonResource;
import pl.sda.training.management.app.domain.model.Lesson;
import pl.sda.training.management.app.domain.model.LessonSubject;
import pl.sda.training.management.app.domain.model.LessonsBlock;
import pl.sda.training.management.app.domain.service.LessonService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static pl.sda.training.management.app.TestUtils.getPage;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class LessonApiServiceTest {

    @Autowired
    private LessonApiService sut;

    @MockBean
    LessonService lessonService;

    @BeforeEach
    public void setup() {
        LessonsBlock lessonsBlock = new LessonsBlock();
        lessonsBlock.setId(1L);

        List<Lesson> lessons = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            Lesson lesson = new Lesson(LessonSubject.of("testLessonSub"+i));
            lesson.setId((long) i);
            lesson.setLessonsBlock(lessonsBlock);
            lessons.add(lesson);
        }

        int page = 1;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);

        when(lessonService.getPage(pageRequest))
                .thenReturn(new PageImpl<>(getPage(lessons, page, size), pageRequest, lessons.size()));

        when(lessonService.getById(1L))
                .thenReturn(lessons.get(0));
    }

    @Test
    void getLessons() {
        //given
        int page = 1;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);

        //when
        CollectionModel<LessonResource> blocks = sut.getLessons(pageRequest);

        //then
        System.out.println(blocks);
        assertThat(blocks.getContent()).hasSize(size);
        assertThat(blocks.getLinks().getLink("self")).isPresent();
        assertThat(blocks.getLinks().getLink("lessons")).isPresent();
        assertThat(blocks.getLinks().getLink("first")).isPresent();
        assertThat(blocks.getLinks().getLink("last")).isPresent();
        assertThat(blocks.getLinks().getLink("next")).isPresent();
        assertThat(blocks.getLinks().getLink("prev")).isPresent();

    }

    @Test
    void getLesson() {
        //given
        long id = 1L;

        //when
        LessonResource lesson = sut.getLesson(id);

        //then
        assertThat(lesson.getLink("self")).isPresent();
        assertThat(lesson.getLink("self").get().getHref())
                .endsWith(linkTo(LessonApiController.class)
                        .slash(1)
                        .toString());

        assertThat(lesson.getLink("lessons-block")).isPresent();
        assertThat(lesson.getLink("lessons-block").get().getHref())
                .endsWith(linkTo(LessonsBlockApiController.class)
                        .slash(1)
                        .toString());
    }
}