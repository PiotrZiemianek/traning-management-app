package pl.sda.training.management.app.api.dto;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import pl.sda.training.management.app.domain.model.LessonsBlock;

import java.util.ArrayList;
import java.util.List;

import static pl.sda.training.management.app.api.dto.LessonsBlockResource.LESSONS_BLOCK_ASSEMBLER;


public class CourseResource extends RepresentationModel<CourseResource> {
    public static final CourseResourceAssembler COURSE_RESOURCE_ASSEMBLER = new CourseResourceAssembler();

    @Getter
    private final String courseName;
    @Getter
    private final List<LessonsBlockResource> lessonsBlocks;

    public CourseResource(String courseName, List<LessonsBlock> lessonsBlocks) {
        this.courseName = courseName;
        this.lessonsBlocks = new ArrayList<>(LESSONS_BLOCK_ASSEMBLER
                .toCollectionModel(lessonsBlocks)
                .getContent());
    }
}
