package pl.sda.training.management.app.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.*;
import pl.sda.training.management.app.domain.repository.CourseEditionRepo;
import pl.sda.training.management.app.exception.CourseEditionNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseEditionService {
    private final CourseEditionRepo courseEditionRepo;
    private final StudentService studentService;

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

    public List<EditionCode> getEditionCodesByCourseIdAndStudentNotParticipated(Long courseId, Login studentLogin) {
        return courseEditionRepo.getEditionCodesByCourseIdAndStudentNotParticipated(courseId, studentService.getByLogin(studentLogin));
    }
    public List<EditionCode> getEditionCodesByCourseId(Long courseId) {
        return courseEditionRepo.getEditionCodesByCourseId(courseId);
    }
}
