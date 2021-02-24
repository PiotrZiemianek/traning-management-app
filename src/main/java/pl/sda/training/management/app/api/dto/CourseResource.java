package pl.sda.training.management.app.api.dto;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import pl.sda.training.management.app.domain.model.LessonsBlock;

import java.util.ArrayList;
import java.util.List;


public class CourseResource extends RepresentationModel<CourseResource> {
    private static final LessonsBlockAssembler LESSONS_BLOCK_ASSEMBLER = new LessonsBlockAssembler();
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
