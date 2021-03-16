package pl.sda.training.management.app.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sda.training.management.app.api.dto.CourseRequest;
import pl.sda.training.management.app.api.dto.CourseResource;
import pl.sda.training.management.app.api.service.CourseApiService;

import static pl.sda.training.management.app.utils.Constants.*;

@RestController
@RequestMapping(path = API_URL + "/courses", produces = API_PRODUCES)
@CrossOrigin("*")
@RequiredArgsConstructor
public class CourseApiController {

    private final CourseApiService courseService;

    @GetMapping
    public CollectionModel<CourseResource> getCourses(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        if (page == null) {
            page = 0;
        }

        if (size == null) {
            size = DEFAULT_API_PAGE_SIZE;
        }

        return courseService.getCourses(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public CourseResource getCourse(@PathVariable Long id) {
        return courseService.getCourse(id);
    }

    @PostMapping
    public ResponseEntity<?> postCourse(@RequestBody CourseRequest courseRequest) {

        CourseResource resource = courseService.save(courseRequest);

        return ResponseEntity
                .created(resource.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putCourse(@RequestBody CourseRequest courseRequest, @PathVariable Long id) {

        courseRequest.setId(id);

        CourseResource resource = courseService.save(courseRequest);

        return ResponseEntity
                .created(resource.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(resource);

    }
}
