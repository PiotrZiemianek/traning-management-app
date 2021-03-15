package pl.sda.training.management.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import pl.sda.training.management.app.domain.model.Course;
import pl.sda.training.management.app.domain.model.CourseName;
import pl.sda.training.management.app.domain.model.LessonsBlock;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class CourseRequest {
    private final Long id;
    private final String name;
    private final List<LessonsBlockRequest> lessonsBlocks;

    public static CourseRequest of(Course course) {
        return new CourseRequest(
                course.getId(),
                course.getName().value(),
                course.getLessonsBlocks().stream()
                        .map(LessonsBlockRequest::of)
                        .collect(Collectors.toList()));
    }

    public Course toCourse() {
        Course course = new Course(CourseName.of(name));
        course.setId(id);
        course.setLessonsBlocks(
                lessonsBlocks.stream()
                        .map(lessonsBlockRequest -> {
                            LessonsBlock lessonsBlock = lessonsBlockRequest.toLessonsBlock();
                            lessonsBlock.setCourse(course);
                            return lessonsBlock;
                        })
                        .collect(Collectors.toList())
        );
        return course;
    }
}
