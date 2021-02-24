package pl.sda.training.management.app.api.dto;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import pl.sda.training.management.app.api.controller.CourseApiController;
import pl.sda.training.management.app.domain.model.Course;

public class CourseResourceAssembler extends RepresentationModelAssemblerSupport<Course, CourseResource> {
    public CourseResourceAssembler() {
        super(CourseApiController.class, CourseResource.class);
    }

    @Override
    public CourseResource toModel(Course entity) {
        return createModelWithId(entity.getId(), entity);
    }

    @Override
    protected CourseResource instantiateModel(Course entity) {
        return CourseResourceMapper.INSTANCE.courseToDto(entity);
    }
}
