package pl.sda.training.management.app.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.Course;
import pl.sda.training.management.app.domain.service.CourseService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseWebService {
    private final CourseService courseService;

    public List<CourseToChoose> getAllCoursesToChoose() {
        return courseService
                .findAll()
                .stream()
                .map(CourseToChoose::of)
                .collect(Collectors.toList());
    }

    public CourseDTO getCourseById(long id) {
        return CourseDTO.of(courseService.getById(id));
    }

    public void save(Course course) {
        courseService.save(course);
    }
}
