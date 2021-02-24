package pl.sda.training.management.app.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.training.management.app.api.dto.CourseResource;
import pl.sda.training.management.app.api.dto.CourseResourceAssembler;
import pl.sda.training.management.app.domain.model.Course;
import pl.sda.training.management.app.domain.service.CourseService;

import java.util.List;

import static pl.sda.training.management.app.utils.Constants.API_PRODUCES;
import static pl.sda.training.management.app.utils.Constants.API_URL;

@RestController
@RequestMapping(path = API_URL + "/course", produces = API_PRODUCES)
@CrossOrigin("*")
@RequiredArgsConstructor
public class CourseApiController {

    private final CourseService courseService;

    @GetMapping
    public CollectionModel<CourseResource> getCourses() {
        List<Course> all = courseService.findAll();
        return new CourseResourceAssembler().toCollectionModel(all);
    }
}
