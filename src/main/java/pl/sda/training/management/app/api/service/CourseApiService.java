package pl.sda.training.management.app.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.api.controller.CourseApiController;
import pl.sda.training.management.app.api.dto.CourseResource;
import pl.sda.training.management.app.domain.model.Course;
import pl.sda.training.management.app.domain.service.CourseService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static pl.sda.training.management.app.api.dto.CourseResource.COURSE_RESOURCE_ASSEMBLER;

@Service
@RequiredArgsConstructor
public class CourseApiService {

    private final CourseService courseService;

    private final PagedResourcesAssembler<Course> pagedResourcesAssembler;

    public CollectionModel<CourseResource> getCourses(Pageable pageable) {

        Page<Course> coursesPage = courseService.getPage(pageable);

        return pagedResourcesAssembler
                .toModel(coursesPage, COURSE_RESOURCE_ASSEMBLER)
                .add(linkTo(CourseApiController.class)
                        .withRel("courses"));
    }

    public CourseResource getCourse(Long id) {
        return COURSE_RESOURCE_ASSEMBLER.toModel(courseService.getById(id));
    }
}
