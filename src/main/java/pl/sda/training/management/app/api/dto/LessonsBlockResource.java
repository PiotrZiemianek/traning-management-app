package pl.sda.training.management.app.api.dto;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import pl.sda.training.management.app.domain.model.Lesson;

import java.util.ArrayList;
import java.util.List;

import static pl.sda.training.management.app.api.dto.LessonResource.LESSON_ASSEMBLER;

@Relation(value = "lessons-block", collectionRelation = "lessons-blocks")
public class LessonsBlockResource extends RepresentationModel<LessonsBlockResource> {
    public static final LessonsBlockAssembler LESSONS_BLOCK_ASSEMBLER = new LessonsBlockAssembler();

    @Getter
    private final String blockName;

    @Getter
    private final List<LessonResource> lessons;

    public LessonsBlockResource(String blockName, List<Lesson> lessons) {
        this.blockName = blockName;
        this.lessons = new ArrayList<>(LESSON_ASSEMBLER
                .toCollectionModel(lessons)
                .getContent());
    }
}
