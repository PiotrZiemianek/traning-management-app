package pl.sda.training.management.app.api.dto;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import pl.sda.training.management.app.api.controller.LessonApiController;
import pl.sda.training.management.app.api.controller.LessonsBlockApiController;
import pl.sda.training.management.app.domain.model.Lesson;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class LessonAssembler extends RepresentationModelAssemblerSupport<Lesson, LessonResource> {
    LessonAssembler() {
        super(LessonApiController.class, LessonResource.class);
    }


    @Override
    public LessonResource toModel(Lesson entity) {
        LessonResource resource = createModelWithId(entity.getId(), entity)
                .add(linkTo(LessonApiController.class)
                        .withRel("lessons"));

        if (entity.getLessonsBlock() != null && entity.getLessonsBlock().getId() != null) {
            resource.add(linkTo(LessonsBlockApiController.class)
                    .slash(entity.getLessonsBlock().getId())
                    .withRel("lessons-block"));
        }

        return resource;
    }

    @Override
    protected LessonResource instantiateModel(Lesson entity) {
        return LessonResourceMapper.INSTANCE.lessonToDto(entity);
    }
}
