package pl.sda.training.management.app.api.dto;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import pl.sda.training.management.app.domain.model.Lesson;

import java.util.ArrayList;
import java.util.List;


public class LessonsBlockResource extends RepresentationModel<LessonsBlockResource> {
    public static final LessonAssembler LESSON_ASSEMBLER = new LessonAssembler();

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
