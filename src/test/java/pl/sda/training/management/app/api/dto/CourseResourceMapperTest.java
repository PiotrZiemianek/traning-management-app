package pl.sda.training.management.app.api.dto;

import org.junit.jupiter.api.Test;
import pl.sda.training.management.app.domain.model.BlockName;
import pl.sda.training.management.app.domain.model.Course;
import pl.sda.training.management.app.domain.model.CourseName;
import pl.sda.training.management.app.domain.model.LessonsBlock;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class CourseResourceMapperTest {

    private final CourseResourceMapper mapper = CourseResourceMapper.INSTANCE;

    @Test
    void courseToDto() {
        //given
        LessonsBlock testBlock = new LessonsBlock(BlockName.of("testBlockName"));
        testBlock.setId(1L);

        Course entity = new Course(CourseName.of("testName"));
        entity.setId(1L);
        entity.getLessonsBlocks().add(testBlock);
        testBlock.setCourse(entity);

        //when
        CourseResource dto = mapper.courseToDto(entity);

        //then
        assertThat(dto.getCourseName()).isEqualTo(entity.getName().value());
        assertThat(dto.getLessonsBlocks()).hasSize(entity.getLessonsBlocks().size());
    }

    @Test
    void resourceToCourse() {
        //given
        LessonsBlock testBlock = new LessonsBlock(BlockName.of("testBlockName"));
        testBlock.setId(1L);

        CourseResource dto = new CourseResource(
                "testName",
                List.of(testBlock));

        //when
        Course entity = mapper.resourceToCourse(dto);

        //then
        assertThat(entity.getName().value()).isEqualTo(dto.getCourseName());
        assertThat(entity.getLessonsBlocks()).hasSize(dto.getLessonsBlocks().size());
    }
}