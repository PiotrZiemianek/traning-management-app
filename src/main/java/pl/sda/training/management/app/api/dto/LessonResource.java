package pl.sda.training.management.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "lesson", collectionRelation = "lessons")
@AllArgsConstructor
public class LessonResource extends RepresentationModel<LessonResource> {
    public static final LessonAssembler LESSON_ASSEMBLER = new LessonAssembler();

    @Getter
    private final String subject;
}
