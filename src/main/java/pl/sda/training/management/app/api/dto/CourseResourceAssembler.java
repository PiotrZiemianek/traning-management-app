package pl.sda.training.management.app.api.dto;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import pl.sda.training.management.app.api.controller.CourseApiController;
import pl.sda.training.management.app.domain.model.Course;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class CourseResourceAssembler extends RepresentationModelAssemblerSupport<Course, CourseResource> {
    CourseResourceAssembler() {
        super(CourseApiController.class, CourseResource.class);
    }

    @Override
    public CourseResource toModel(Course entity) {
        return createModelWithId(entity.getId(), entity)
                .add(linkTo(CourseApiController.class).withRel("courses"));
    }

    @Override
    protected CourseResource instantiateModel(Course entity) {
        return CourseResourceMapper.INSTANCE.courseToDto(entity);
    }
}
