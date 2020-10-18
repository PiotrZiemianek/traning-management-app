package pl.sda.training.management.app.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.Course;
import pl.sda.training.management.app.domain.repository.CourseRepo;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepo courseRepo;

    public Course save(Course course) {
        return courseRepo.save(course);
    }

}
