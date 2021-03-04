package pl.sda.training.management.app.api.dto;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import pl.sda.training.management.app.api.controller.CourseApiController;
import pl.sda.training.management.app.api.controller.LessonsBlockApiController;
import pl.sda.training.management.app.domain.model.LessonsBlock;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class LessonsBlockAssembler extends RepresentationModelAssemblerSupport<LessonsBlock, LessonsBlockResource> {
    LessonsBlockAssembler() {
        super(LessonsBlockApiController.class, LessonsBlockResource.class);
    }


    @Override
    public LessonsBlockResource toModel(LessonsBlock entity) {
        LessonsBlockResource resource = createModelWithId(entity.getId(), entity)
                .add(linkTo(LessonsBlockApiController.class)
                        .withRel("lessons-block"));

        if (entity.getCourse() != null && entity.getCourse().getId() != null) {
            resource.add(linkTo(CourseApiController.class)
                    .slash(entity.getCourse().getId())
                    .withRel("course"));
        }
        return resource;
    }

    @Override
    protected LessonsBlockResource instantiateModel(LessonsBlock entity) {
        return LessonsBlockResourceMapper.INSTANCE.lessonsBlockToDto(entity);
    }
}
