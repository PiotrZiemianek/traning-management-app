package pl.sda.training.management.app.api.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sda.training.management.app.domain.model.BlockName;
import pl.sda.training.management.app.domain.model.Lesson;
import pl.sda.training.management.app.domain.model.LessonSubject;
import pl.sda.training.management.app.domain.model.LessonsBlock;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LessonsBlockResourceMapperTest {

    private final LessonsBlockResourceMapper mapper = LessonsBlockResourceMapper.INSTANCE;
    private Lesson lesson;

    @BeforeEach
    void setup() {
        lesson = new Lesson(LessonSubject.of("testSub"));
        lesson.setId(1L);
    }

    @Test
    void lessonsBlockToDto() {
        //given
        LessonsBlock entity = new LessonsBlock();
        entity.setBlockName(BlockName.of("testBlockName"));
        entity.getLessons().add(lesson);

        //when
        LessonsBlockResource dto = mapper.lessonsBlockToDto(entity);

        //then
        assertThat(dto.getBlockName()).isEqualTo(entity.getBlockName().value());
        assertThat(dto.getLessons()).hasSize(entity.getLessons().size());
    }

    @Test
    void resourceToLessonsBlock() {
        //given
        LessonsBlockResource dto = new LessonsBlockResource("testName", List.of(lesson));

        //when
        LessonsBlock entity = mapper.resourceToLessonsBlock(dto);

        //then
        assertThat(entity.getBlockName().value()).isEqualTo(dto.getBlockName());
        assertThat(entity.getLessons()).hasSize(dto.getLessons().size());
    }
}