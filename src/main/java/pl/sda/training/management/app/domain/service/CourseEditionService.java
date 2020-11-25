package pl.sda.training.management.app.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.Course;
import pl.sda.training.management.app.domain.model.CourseEdition;
import pl.sda.training.management.app.domain.model.LessonDetails;
import pl.sda.training.management.app.domain.repository.CourseEditionRepo;
import pl.sda.training.management.app.exception.CourseEditionNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseEditionService {
    private final CourseEditionRepo courseEditionRepo;

    public CourseEdition fromTemplate(Course course) {
        CourseEdition courseEdition = new CourseEdition(course);
        course.getLessonsBlocks().forEach(lessonsBlock -> lessonsBlock.getLessons()
                .stream()
                .map(LessonDetails::new)
                .forEach(lessonDetails -> {
                    courseEdition.getLessonsDetails().add(lessonDetails);
                    lessonDetails.setCourseEdition(courseEdition);
                }));
        return courseEdition;
    }

    public void save(CourseEdition courseEdition) {
        courseEditionRepo.save(courseEdition);
    }

    public List<CourseEdition> getAll() {
        return courseEditionRepo.findAll();
    }

    public CourseEdition getById(Long id) {
        return courseEditionRepo.findById(id)
                .orElseThrow(() -> new CourseEditionNotFoundException("CourseEdition with id: " + id + " not found."));
    }
}
