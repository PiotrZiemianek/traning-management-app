package pl.sda.training.management.app.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.*;
import pl.sda.training.management.app.domain.repository.CourseEditionRepo;
import pl.sda.training.management.app.exception.CourseEditionNotFoundException;

import java.util.List;
import java.util.Optional;

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

    public List<EditionCode> getEditionCodesByCourseIdWhereStudentNotParticipated(Long courseId, Login studentLogin) {
        return courseEditionRepo.getEditionCodesByCourseIdWhereStudentNotParticipated(courseId, studentService.getByLogin(studentLogin));
    }

    public List<EditionCode> getEditionCodesWhereStudentIsNotParticipant(Login studentLogin) {
        return courseEditionRepo.getEditionCodesWhereStudentIsNotParticipant(studentService.getByLogin(studentLogin));
    }

    public List<EditionCode> getEditionCodesByCourseId(Long courseId) {
        return courseEditionRepo.getEditionCodesByCourseId(courseId);
    }

    public void deleteStudentFromEdition(Login login, EditionCode editionCode) {
        Student student = studentService.getByLogin(login);
        CourseEdition courseEdition = courseEditionRepo.findByEditionCode(editionCode)
                .orElseThrow(() -> new CourseEditionNotFoundException("CourseEdition with code: " + editionCode.value() + " not found."));

        courseEdition.getStudents().remove(student);
        student.getCoursesEditions().remove(courseEdition);

        courseEditionRepo.save(courseEdition);
        studentService.save(student);
    }

    public void addStudentToEdition(Login login, EditionCode editionCode) {
        Student student = studentService.getByLogin(login);
        CourseEdition courseEdition = courseEditionRepo.findByEditionCode(editionCode)
                .orElseThrow(() -> new CourseEditionNotFoundException("CourseEdition with code: " + editionCode.value() + " not found."));

        courseEdition.getStudents().add(student);
        student.getCoursesEditions().add(courseEdition);


        student.getSubmissions().removeIf(studentSubmission -> studentSubmission.getCourseEdition().equals(courseEdition));

        courseEditionRepo.save(courseEdition);
        studentService.save(student);

    }

    public boolean existsByEditionCode(EditionCode editionCode) {
        return courseEditionRepo.existsByEditionCode(editionCode);
    }

    public Optional<CourseEdition> findByEditionCode(EditionCode editionCode) {
        return courseEditionRepo.findByEditionCode(editionCode);
    }
}
