package pl.sda.training.management.app.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.Course;
import pl.sda.training.management.app.domain.repository.CourseRepo;
import pl.sda.training.management.app.exception.CourseNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepo courseRepo;

    public Course save(Course course) {
        return courseRepo.save(course);
    }

    public List<Course> findAll() {
        return courseRepo.findAll();
    }

    public Page<Course> getPage(Pageable pageable) {
        return courseRepo.findAll(pageable);
    }

    public Course getById(long id) {
        return courseRepo.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course with id: " + id + " not found."));
    }
}
