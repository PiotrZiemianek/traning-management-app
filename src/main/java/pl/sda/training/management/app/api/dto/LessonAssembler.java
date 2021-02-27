package pl.sda.training.management.app.api.dto;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import pl.sda.training.management.app.api.controller.LessonApiController;
import pl.sda.training.management.app.domain.model.Lesson;

public class LessonAssembler extends RepresentationModelAssemblerSupport<Lesson, LessonResource> {
    LessonAssembler() {
        super(LessonApiController.class, LessonResource.class);
    }


    @Override
    public LessonResource toModel(Lesson entity) {
        return createModelWithId(entity.getId(), entity);
    }

    @Override
    protected LessonResource instantiateModel(Lesson entity) {
        return LessonResourceMapper.INSTANCE.lessonToDto(entity);
    }
}
