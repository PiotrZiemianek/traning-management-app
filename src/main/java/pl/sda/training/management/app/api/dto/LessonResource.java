package pl.sda.training.management.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
public class LessonResource extends RepresentationModel<LessonResource> {
    public static final LessonAssembler LESSON_ASSEMBLER = new LessonAssembler();

    @Getter
    private final String subject;
}
