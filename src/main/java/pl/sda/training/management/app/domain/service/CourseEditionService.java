package pl.sda.training.management.app.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.Course;
import pl.sda.training.management.app.domain.model.CourseEdition;
import pl.sda.training.management.app.domain.model.LessonDetails;
import pl.sda.training.management.app.domain.repository.CourseEditionRepo;

@Service
@RequiredArgsConstructor
public class CourseEditionService {
    private final CourseEditionRepo courseEditionRepo;

    public CourseEdition fromTemplate(Course course) {
        CourseEdition courseEdition = new CourseEdition(course);
        course.getLessonsBlocks().forEach(lessonsBlock -> lessonsBlock.getLessons()
                .stream()
                .map(LessonDetails::new)
                .forEach(courseEdition.getLessonsDetails()::add));
        return courseEdition;
    }

    public void save(CourseEdition courseEdition) {
        courseEditionRepo.save(courseEdition);
    }
}
