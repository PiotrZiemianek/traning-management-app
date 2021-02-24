package pl.sda.training.management.app.api.dto;

import org.junit.jupiter.api.Test;
import pl.sda.training.management.app.domain.model.Lesson;
import pl.sda.training.management.app.domain.model.LessonSubject;

import static org.assertj.core.api.Assertions.assertThat;

public class LessonResourceMapperTest {
    private final LessonResourceMapper mapper = LessonResourceMapper.INSTANCE;


    @Test
    void lessonToDto() {
        //given
        Lesson entity = new Lesson(LessonSubject.of("testSub"));

        //when
        LessonResource dto = mapper.lessonToDto(entity);

        //then
        assertThat(dto.getSubject()).isEqualTo(entity.getSubject().value());
    }

    @Test
    void resourceToLesson() {
        //given
        LessonResource dto = new LessonResource("testSub");

        //when
        Lesson entity = mapper.resourceToLesson(dto);

        //then
        assertThat(entity.getSubject().value()).isEqualTo(dto.getSubject());
    }
}
